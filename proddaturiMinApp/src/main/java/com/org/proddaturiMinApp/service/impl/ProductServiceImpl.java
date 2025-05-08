package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.ProductService;
import com.org.proddaturiMinApp.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getFilteredProducts(String categoryName, int i) {
        Pageable pageable= PageRequest.of(i,10);
        String id =categoryRepository.findByName(categoryName).get().getId();
       return  productRepository.findByCategory(new ObjectId(categoryName),pageable);

    }

    public List<Product> getProducts(String categoryName) {
        return getFilteredProducts(categoryName, 0);
    }

    public List<Product> getProductsViaNextValue(String categoryName, int i) {
        return getFilteredProducts(categoryName, i);
    }


    public List<Product> allProducts() {
        List<Product> allProducts = productRepository.findAll();
        log.info("all products are : {}",allProducts);
        return allProducts;
    }

    public Optional<Product> getProductsById(String id) {
        return productRepository.findById(id);
    }


    public String getCategoryNameById(String categoryId) {
        return categoryRepository.findAll().stream()
                .filter(category -> Objects.equals(category.getId(), categoryId))
                .map(Category::getName)
                .findFirst()
                .orElse(CommonConstants.categoryNotFound + " with id " + categoryId);
    }

    public List<Product> getProductsByName(String productName,String categoryName) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> filterProducts = new ArrayList<>();
        if(Objects.isNull(categoryName))
        {
            for(Product product:allProducts)
            {
                String productNameLower = productName.toLowerCase();
                String productNameInList = product.getName().toLowerCase();

                if (productNameLower.contains(productNameInList) || productNameInList.contains(productNameLower)) {

                    filterProducts.add(product);
                }
            }
        }
        else{
            List<Product> categoryBasedProducts= getAllProductsByCategory(categoryName);
            for(Product product:categoryBasedProducts)
            {
                if(productName.equalsIgnoreCase(product.getName()))
                {
                    filterProducts.add(product);
                }
            }

        }
        return filterProducts;
    }

    public List<Product> getAllProductsByCategory(String categoryName) {
        List<Product> filteredProducts = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();
        int i=0;
        while (i < allProducts.size()) {
            Product product = allProducts.get(i);
            if (Objects.isNull(product.getCategory()) ) {
                i++;
                continue; // skip if category is null
            }

            String productCategoryName = getCategoryNameById(product.getCategory().toString());
            if (productCategoryName.equalsIgnoreCase(categoryName)) {
               filteredProducts.add(product);
            }
            i++;
        }

        return filteredProducts;

    }

}
