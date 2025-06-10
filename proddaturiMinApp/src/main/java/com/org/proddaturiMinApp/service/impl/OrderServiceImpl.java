package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.OrdersCartDTO;
import com.org.proddaturiMinApp.dto.ProductInCartDTO;
import com.org.proddaturiMinApp.emums.OrderStatus;
import com.org.proddaturiMinApp.emums.PaymentMethod;
import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.exception.DetailsNotFoundException;
import com.org.proddaturiMinApp.model.*;
import com.org.proddaturiMinApp.repository.*;
import com.org.proddaturiMinApp.service.CartService;
import com.org.proddaturiMinApp.service.OrderService;
import com.org.proddaturiMinApp.utils.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CartRepository cartRespsitory;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private DeliveryFeesRepository deliveryFeesRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public ResponseEntity<Orders> initiateOrder(String phoneNumber, String addressId) {

        Cart cart= cartRespsitory.findById(phoneNumber).orElseThrow(()-> new DetailsNotFoundException("cart is empty"));
        Address address=addressRepository.findById(addressId).orElseThrow(()->new DetailsNotFoundException("Address not found"));
        List<DeliveryFees> deliveryFeesList = deliveryFeesRepository.findAll();
        if(deliveryFeesList.isEmpty()){
            throw new DetailsNotFoundException("Delivery fee not found");
        }
        Integer deliveryFees = deliveryFeesList.get(0).getDeliveryFee();
        Orders orders= new Orders();
        orders.setId(UUID.randomUUID().toString());
        orders.setPhoneNumber(phoneNumber);
        orders.setOrderStatus(OrderStatus.INITIATED);
        orders.setOrdersCartDTO(new OrdersCartDTO(cart));
        orders.setDeliveryAddress(address);
        // need to update in future
        orders.setPaymentMethod(PaymentMethod.CASH_ON_DELIVERY);
        orders.setDeliveryCharges(Double.valueOf(deliveryFees));
        orders.setTotalPayable(cart.getTotalPrice()+deliveryFees);
        orders.setCreatedAt(LocalDateTime.now());
        orders.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(orders);
        // after saving the order detials now we need to decrease the quantiy by one in the products
        for( String productId:cart.getProductsMap().keySet()){
            Product product=productRepository.findById(productId).orElseThrow(()-> new DetailsNotFoundException("product not found"));
            product.setStock(product.getStock()-cart.getProductsMap().get(productId).getQuantity());
            productRepository.save(product);
        }
       return ResponseEntity.status(HttpStatus.CREATED).body(orders);
    }

    @Override
    public ResponseEntity<Orders> confirmOrder(String phoneNumber, String orderId) throws CannotModifyException {
        Orders order=orderRepository.findById(orderId).orElseThrow(()->new DetailsNotFoundException("Order details are not found"));
        if(!Objects.equals(OrderStatus.INITIATED,order.getOrderStatus())){
            throw new CannotModifyException("Order status is "+order.getOrderStatus()+" cannot be modified");
        }
        order.setOrderStatus(OrderStatus.PENDING);
        order.setUpdatedAt(LocalDateTime.now());
        // Now onwars admin will take the aciton
        orderRepository.save(order);
        cartService.emptyCart(phoneNumber);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<Orders> getOrderDetails(String orderId) throws DetailsNotFoundException {
        Orders order=orderRepository.findById(orderId).orElseThrow(()->new DetailsNotFoundException("Order details are not found"));
        return ResponseEntity.status(HttpStatus.FOUND).body(order);
    }

    @Override
    public ResponseEntity<List<Orders>> getAllOrderDetails(String phoneNumber) {
        Pageable pageable= PageRequest.ofSize(CommonConstants.ORDER_PAGINATION_RANGE);
       return ResponseEntity.status(HttpStatus.FOUND).body(orderRepository.findByPhoneNumber(phoneNumber,pageable));

    }

    @Override
    public ResponseEntity<Set<Map<Object, Object>>> getCurrentOrders(String phoneNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
        query.addCriteria(Criteria.where("orderStatus").in(OrderStatus.PENDING, OrderStatus.CONFIRMED, OrderStatus.SHIPPED));
        List<Orders> orders = mongoTemplate.find(query, Orders.class);

        return ResponseEntity.ok(orders.stream().map(order -> {
            Map<Object, Object> map = new HashMap<>();
            map.put("id", order.getId());
            map.put("orderStatus", order.getOrderStatus());
            return map;
        }).collect(Collectors.toSet()));
    }


    @Scheduled(fixedRate = 300000) // every 5 minutes , it is 1000 milli seconds , which here is 300 seconds
    public void cancelStaleOrders() {
        List<Orders> staleOrders = orderRepository.findByOrderStatus(OrderStatus.INITIATED);
        for (Orders order : staleOrders) {
            if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(15))) {
                Collection<ProductInCartDTO> productsList = order.getOrdersCartDTO().getProductsList();
                if(Objects.isNull(productsList)||productsList.isEmpty()){
                    continue;
                }
                for(ProductInCartDTO productInCart : productsList){
                    String productId = productInCart.getId().toString();
                    int quantity = productInCart.getQuantity();
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new DetailsNotFoundException("Product not found: " + productId));

                    product.setStock(product.getStock() + quantity);
                    productRepository.save(product);
                }
                order.setOrderStatus(OrderStatus.EXPIRED);
                orderRepository.save(order);
            }
        }
    }

}
