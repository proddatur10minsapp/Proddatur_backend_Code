package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.CartInputDTO;
import com.org.proddaturiMinApp.dto.ProductInCartDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Cart;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.CartRespsitory;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.CartService;
import com.org.proddaturiMinApp.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRespsitory cartRespsitory;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public ResponseEntity<Map<String, Object>> addProductToCart(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton {
        if (Objects.isNull(cartInputDTO.getProductId()) || Objects.isNull(cartInputDTO.getCatagoryName()) || Objects.isNull(cartInputDTO.getQuantity())) {
            throw new InputFieldRequried("productId or CagatoryName or Quantity should be not null");
        }
        Cart cart = cartRespsitory.findById(phoneNumber).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setId(phoneNumber);
            newCart.setPhoneNumber(phoneNumber);
            return newCart;
        });
        if (Objects.nonNull(cart.getProductsMap()) && cart.getProductsMap().containsKey(cartInputDTO.getProductId())) {
            throw new CommonExcepton("Product is already Exist in Cast , please go to cart");
        }
        cart.setUpdatedAt(LocalDateTime.now());

        ProductInCartDTO productInCart = new ProductInCartDTO();
        productInCart.setId(cartInputDTO.getProductId());
        productInCart.setCategoryName(cartInputDTO.getCatagoryName());
        productInCart.setQuantity(cartInputDTO.getQuantity());
        updateProductInCartDTO(productInCart);
        productInCart.setUpdatedAt(LocalDateTime.now());
        if (Objects.isNull(cart.getProductsMap())) {
            cart.setProductsMap(new HashMap<>());
        }
        Map<String, ProductInCartDTO> productsMap = cart.getProductsMap();
        productsMap.put(String.valueOf(productInCart.getId()), productInCart);

        updateCart(cart, productsMap);
        cartRespsitory.save(cart);
        Map<String, Object> reponse = new HashMap<>();
        reponse.put("message", "Product add to cart Successfully");
        reponse.put("totalProductsInCart", cart.getTotalItemsInCart());
        return ResponseEntity.status(HttpStatus.CREATED).body(reponse);
    }


    public ResponseEntity<Cart> getAllItemsInCart(String phoneNumber) {
        Optional<Cart> optionalCart = cartRespsitory.findById(phoneNumber);
        if (optionalCart.isEmpty()) {
            throw new DetailsNotFound("Cart is Empty");
        }
         Cart cart = optionalCart.get();

        if (Objects.isNull(cart.getProductsMap()) || cart.getProductsMap().isEmpty()) {
            throw new DetailsNotFound("Cart is Empty");
        }
        Map<String, ProductInCartDTO> productsMap = cart.getProductsMap();
        productsMap.keySet().forEach(key -> {
            ProductInCartDTO product = productsMap.get(key);
            updateProductInCartDTO(product);
        });
        updateCart(cart, productsMap);
        return ResponseEntity.status(HttpStatus.FOUND).body(cart);

    }

    public ResponseEntity<Cart> updatePoductInCart(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton {
        if (Objects.isNull(cartInputDTO.getProductId()) || Objects.isNull(cartInputDTO.getQuantity())) {
            throw new InputFieldRequried("productId or CagatoryName or Quantity should be not null");
        }
        Cart cart = cartRespsitory.findById(phoneNumber).get();

        if (Objects.isNull(cart.getProductsMap()) || cart.getProductsMap().isEmpty() || !cart.getProductsMap().containsKey(String.valueOf(cartInputDTO.getProductId()))) {
            throw new CommonExcepton("Product is dose not Exist in Cart , please add product first");
        }

        Map<String, ProductInCartDTO> productsMap = cart.getProductsMap();
        if (cartInputDTO.getQuantity() <= 0) {
            productsMap.remove(String.valueOf(cartInputDTO.getProductId()));
            log.info("Product removed from cart successfully");

        } else {
            ProductInCartDTO productInCartDTO = productsMap.get(String.valueOf(cartInputDTO.getProductId()));
            productInCartDTO.setQuantity(cartInputDTO.getQuantity());
            productInCartDTO = updateProductInCartDTO(productInCartDTO);
            log.info("Product incremented to cart");
        }

        updateCart(cart, productsMap);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRespsitory.save(cart);
        log.info("Product add to cart :{}", cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getTotalNumberOfProductsInCart(String phoneNumber) {

        int totalProductsInCart= cartRespsitory.findById(phoneNumber)
                .map(cart -> cart.getTotalItemsInCart())
                .orElseGet(() -> 0);

        Map<String, Object> reponse = new HashMap<>();
        reponse.put("totalProductsInCart", totalProductsInCart);
        return ResponseEntity.ok(reponse);
    }


    private ProductInCartDTO updateProductInCartDTO(ProductInCartDTO productInCart) {
        Optional<Product> productDetails = productRepository.findById(String.valueOf(productInCart.getId()));
        if (productDetails.isEmpty()) {
            throw new DetailsNotFound("No Prouduct Found for id " + productInCart.getId());
        }
        Product product = productDetails.get();
        productInCart.setId(product.getId());
        productInCart.setProductName(product.getName());
        productInCart.setImage(product.getImage());
        productInCart.setCategory(product.getCategory());
        productInCart.setIsProductAvailabe(product.getStock() > 0 ? CommonConstants.TRUE : CommonConstants.FALSE);
        productInCart.setPrice(product.getPrice());
        productInCart.setTotalCurrentPrice(productInCart.getPrice() * productInCart.getQuantity());
        productInCart.setDiscountedPrice(product.getDiscountPrice());
        productInCart.setTotalDiscountedAmount(productInCart.getTotalCurrentPrice() - (productInCart.getDiscountedPrice() * productInCart.getQuantity()));
        productInCart.setTotalPrice(productInCart.getTotalCurrentPrice() - productInCart.getTotalDiscountedAmount());
        double percentage=(productInCart.getTotalDiscountedAmount()*100)/productInCart.getTotalCurrentPrice();
        productInCart.setDiscountPercentage((int)Math.ceil(percentage));
        return productInCart;
    }

    private static void updateCart(Cart cart, Map<String, ProductInCartDTO> productsMap) {
        cart.setTotalItemsInCart(0);
        cart.setCurrentTotalPrice(0.0);
        cart.setDiscountedAmount(0.0);

        for (String key : productsMap.keySet()) {
            ProductInCartDTO product = productsMap.get(key);
            if (product.getIsProductAvailabe()) {
                cart.setCurrentTotalPrice(cart.getCurrentTotalPrice() + product.getTotalCurrentPrice());
                cart.setDiscountedAmount(cart.getDiscountedAmount() + product.getTotalDiscountedAmount());
                cart.setTotalItemsInCart(cart.getTotalItemsInCart() + product.getQuantity());
            }
        }
        cart.setTotalPrice(cart.getCurrentTotalPrice() - cart.getDiscountedAmount());
    }

}
