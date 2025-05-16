package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
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


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommonUtils commonUtils;

    public List<Product> getFilteredProducts(String categoryName, int i) throws CommonExcepton {
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
        return productRepository.findByCategory(objectId, pageable);
    }

    public List<Product> getProducts(String categoryName) throws CommonExcepton {
        return getFilteredProducts(categoryName, 0);
    }

    public List<Product> getProductsViaNextValue(String categoryName, int i) throws CommonExcepton {
        return getFilteredProducts(categoryName, i);
    }


    public List<Product> allProducts() {
        List<Product> allProducts = productRepository.findAll();
        log.info("all products are : {}", allProducts);
        return allProducts;
    }

    public Product getProductsById(String id) throws CommonExcepton {
        if(Objects.isNull(id)){
            log.error("productId can't be null");
        }
        Optional<Product> product = productRepository.findById(id);
        if(product.isEmpty()){
            log.info("No product found for the product id {}",id);
            throw new DetailsNotFound("No product found for the product id "+id);
        }
        return product.get();
    }


    public Set<Product> getFilteredProductByName(String productName) throws InputFieldRequried {

        if(Objects.isNull(productName)){
            log.info("product name is null");
            throw new InputFieldRequried("product name is requried");
        }
        List<String> listOfSerach = Arrays.stream(productName.split(" ")).toList();
        Pageable pageable = PageRequest.of(0, CommonConstants.paginationRange);
        Set<Product> resultSet = listOfSerach.stream()
                .flatMap(term -> productRepository.findByNameContainingIgnoreCase(term, pageable).stream())
                .collect(Collectors.toSet());

        return resultSet;

    }

    //    public String getCategoryNameById(String categoryId) {
//        return categoryRepository.findAll().stream()
//                .filter(category -> Objects.equals(category.get_id(), categoryId))
//                .map(Category::getName)
//                .findFirst()
//                .orElse(CommonConstants.categoryNotFound + " with id " + categoryId);
//    }


}
