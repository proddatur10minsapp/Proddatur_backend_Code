package com.org.proddaturiMinApp.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;
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
import java.util.stream.Stream;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommonUtils commonUtils;

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

    public  Set<HashMap<String, Object>> getProducts(String categoryName) throws CommonExcepton {
        return getFilteredProducts(categoryName, 0);
    }

    public  Set<HashMap<String, Object>> getProductsViaNextValue(String categoryName, int i) throws CommonExcepton {
        return getFilteredProducts(categoryName, i);
    }


    public ProductDTO getProductsById(String id) throws CommonExcepton {
        if(Objects.isNull(id)){
            log.error("productId can't be null");
        }
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            log.info("No product found for the product id {}",id);
            throw new DetailsNotFound("No product found for the product id "+id);
        }
        return getProductDTO(product.get());
    }


    public Set<HashMap<String, Object>> getFilteredProductByName(String productName) throws InputFieldRequried {
        if(Objects.isNull(productName)){
            log.info("product name is null");
            throw new InputFieldRequried("product name is requried");
        }
        List<String> listOfSerach = Arrays.stream(productName.split(" ")).toList();
        Pageable pageable = PageRequest.of(0, CommonConstants.paginationRange);

        Set<HashMap<String, Object>> resultSet = listOfSerach.stream().flatMap(searchterm -> productRepository.findByNameContainingIgnoreCase(searchterm, pageable).stream().map(this::getSearchProductRetrunMap)).collect(Collectors.toSet());
        return resultSet;

    }

    private Set<HashMap<String, Object>> getFilteredProducts(String categoryName, int i) throws CommonExcepton {
        Pageable pageable = PageRequest.of(i, CommonConstants.paginationRange);
        String id =null;
        try {
            id = String.valueOf(categoryRepository.findByName(categoryName).get().get_id());
        }
        catch (Exception e){
            log.info("failed to get the id for categoryName {}",categoryName);
            throw new DetailsNotFound("Details Not found for the catagory Name "+categoryName);
        }

        ObjectId objectId=commonUtils.convertToObjectId(id);
        if(Objects.isNull(objectId)){
            throw new CommonExcepton("Cannot able to convert to object Id");
        }
        Set<HashMap<String, Object>> retunVal = productRepository.findByCategory(objectId, pageable).stream().map(this::getSearchProductRetrunMap).collect(Collectors.toSet());
        return retunVal;
    }

    private ProductDTO getProductDTO(Product product){

        Optional<Category> catagory = categoryRepository.findById(String.valueOf(product.getCategory()));
        if(catagory.isEmpty()){
            throw new DetailsNotFound("Catagory may be deleted for the product , catagoryid {}" +product.getCategory());
        }
        return new ProductDTO(product,catagory.get().getName());
    }
    private HashMap<String, Object> getSearchProductRetrunMap(Product product){
        HashMap<String, Object> returnProductMap=new HashMap<>();
        returnProductMap.put(ID,product.getId().toString());
        returnProductMap.put(NAME,product.getName());
        returnProductMap.put(IMAGE,product.getImage());
        returnProductMap.put(PRICE,product.getPrice());
        returnProductMap.put(DISCOUNTED_PRICE,product.getDiscountPrice());
        returnProductMap.put(QUANTITY,product.getQuantity());
        returnProductMap.put(STOCK,product.getStock());
        returnProductMap.put(CATEGORY,product.getCategory().toString());
        return returnProductMap;
    }


}
