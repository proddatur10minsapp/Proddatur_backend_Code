package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.dto.ProductInCartDTO;
import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.DetailsNotFoundException;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Cart;
import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.CartRepository;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.ProductService;
import com.org.proddaturiMinApp.utils.CommonConstants;
import com.org.proddaturiMinApp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommonUtils commonUtils;

    @Autowired
    private CartRepository cartRespsitory;

    private String ID= "id";
    private String NAME="name";
    private String IMAGE="image";
    private String GALLERY="gallery";
    private String PRICE= "price";
    private String DISCOUNTED_PRICE="discountPrice";
    private String QUANTITY="quantity";
    private String DESCRIPTION="description";
    private String KEY_FEATURES= "keyFeatures";
    private String SPECIFICATIONS="specifications";
    private String STOCK="stock";
    private String  CATEGORY="category";

    public  Set<HashMap<String, Object>> getProducts(String categoryName,String phoneNumber) throws CommonException {
        return getFilteredProducts(categoryName, 0,CommonConstants.PAGINATION_RANGE,phoneNumber);
    }

    public  Set<HashMap<String, Object>> getProductsViaNextValue(String categoryName, int i,String phoneNumber) throws CommonException {
        return getFilteredProducts(categoryName, i, CommonConstants.PAGINATION_RANGE,phoneNumber);
    }


    public ProductDTO getProductsById(String id,String phoneNumber) throws CommonException {
        if(Objects.isNull(id)){
            log.error("productId can't be null");
        }
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            log.info("No product found for the product id {}",id);
            throw new DetailsNotFoundException("No product found for the product id "+id);
        }
        Map<String, ProductInCartDTO> cartProductsMap=null;
        if(Objects.nonNull(phoneNumber)){
            cartProductsMap=cartRespsitory.findById(phoneNumber).map(Cart::getProductsMap).orElse(null);
        }
        return getProductDTO(product.get(),cartProductsMap);
    }


    public Set<HashMap<String, Object>> getFilteredProductByName(String productName,String phoneNumber) throws InputFieldRequried {
        if(Objects.isNull(productName)){
            log.info("product name is null");
            throw new InputFieldRequried("product name is requried");
        }
        List<String> listOfSerach = Arrays.stream(productName.split(" ")).toList();
        Pageable pageable = PageRequest.of(0, CommonConstants.PAGINATION_RANGE);
        Map<String, ProductInCartDTO> finalCartProductsMap;
        if(Objects.nonNull(phoneNumber)){
            finalCartProductsMap=cartRespsitory.findById(phoneNumber).map(Cart::getProductsMap).orElse(null);
        } else {
            finalCartProductsMap = null;
        }

        Set<HashMap<String, Object>> resultSet = listOfSerach.stream().flatMap(searchterm -> productRepository.findByNameContainingIgnoreCase(searchterm, pageable).stream().map(product -> getSearchProductRetrunMap(product, finalCartProductsMap))).collect(Collectors.toSet());
        return resultSet;

    }

    @Override
    public Set<HashMap<String, Object>> getProductsForTrends(String categoryName, String phoneNumber, Integer paginationRange) throws CommonException {
        return getFilteredProducts(categoryName, 0,paginationRange,phoneNumber);
    }

    private Set<HashMap<String, Object>> getFilteredProducts(String categoryName, int pageNumber,int paginationRange,String phoneNumber) throws CommonException {
        Pageable pageable = PageRequest.of(pageNumber, paginationRange);
        String id =null;
        try {
            id = String.valueOf(categoryRepository.findByName(categoryName).get().get_id());
        }
        catch (Exception e){
            log.info("failed to get the id for categoryName {}",categoryName);
            throw new DetailsNotFoundException("Details Not found for the catagory Name "+categoryName);
        }

        ObjectId objectId=commonUtils.convertToObjectId(id);
        if(Objects.isNull(objectId)){
            throw new CommonException("Cannot able to convert to object Id");
        }
        // for fetching the products
        Map<String, ProductInCartDTO> cartMap;
        if(Objects.nonNull(phoneNumber)){
            cartMap=cartRespsitory.findById(phoneNumber).map(Cart::getProductsMap).orElse(null);
        } else {
            cartMap = null;
        }
        Set<HashMap<String, Object>> retunVal = productRepository.findByCategory(objectId, pageable).stream().map(product->getSearchProductRetrunMap(product,cartMap)).collect(Collectors.toSet());
        return retunVal;
    }

    private ProductDTO getProductDTO(Product product,Map<String, ProductInCartDTO> cartProductsMap){

        Optional<Category> catagory = categoryRepository.findById(String.valueOf(product.getCategory()));
        if(catagory.isEmpty()){
            throw new DetailsNotFoundException("Catagory may be deleted for the product , catagoryid {}" +product.getCategory());
        }
        return new ProductDTO(product,catagory.get().getName(),cartProductsMap);
    }
    private HashMap<String, Object> getSearchProductRetrunMap(Product product ,Map<String, ProductInCartDTO> cartProductsMap){
        String productID=product.getId().toString();
        HashMap<String, Object> returnProductMap=new HashMap<>();
        returnProductMap.put(ID,productID);
        returnProductMap.put(NAME,product.getName());
        returnProductMap.put(IMAGE,product.getImage());
        returnProductMap.put(PRICE,product.getPrice());
        returnProductMap.put(DISCOUNTED_PRICE,product.getDiscountPrice());
        returnProductMap.put(QUANTITY,product.getQuantity());
        returnProductMap.put(STOCK,product.getStock());
        returnProductMap.put(CATEGORY,product.getCategory().toString());

        returnProductMap.put("isPresentInWishList",CommonConstants.FALSE);
        returnProductMap.put("isPresentInCart",CommonConstants.FALSE);


        // now need to check the cart is null or not if not null if the products present in the cart need to update here
        if(Objects.nonNull(cartProductsMap)){
            if(cartProductsMap.containsKey(productID)){
                // update the values
                returnProductMap.put("isPresentInCart",CommonConstants.TRUE);
                returnProductMap.put("quantityInCart",cartProductsMap.get(productID).getQuantity());
            }
        }
        return returnProductMap;
    }


}
