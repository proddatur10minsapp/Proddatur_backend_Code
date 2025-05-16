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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getFilteredProducts(String categoryName) {

        return getAllProductsByCategory(categoryName);

    }

    public List<Product> getProducts(String categoryName) {

        return getFilteredProducts(categoryName);
    }



    public List<Product> allProducts() {
        List<Product> allProducts = productRepository.findAll();
        log.info("all products are : {}", allProducts);
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


    public List<Product> getProductsByName(String productName) {
        List<Product> allProducts = productRepository.findAll();
        List<Product> filteredProducts = new ArrayList<>();

    
        String[] keywords = productName.toLowerCase().split("\\s+");

        for (Product product : allProducts) {
            String productNameLower = product.getName().toLowerCase();

            boolean matchFound = false;
            for (String word : keywords) {
               
                if (word.endsWith("s") && word.length() > 3) {
                    word = word.substring(0, word.length() - 1);
                }

                String regex = ".*" + Pattern.quote(word) + ".*";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(productNameLower);

                if (matcher.matches()) {
                    matchFound = true;
                    break; 
                }
            }

            if (matchFound) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    public List<Product> getAllProductsByCategory(String categoryName) {
        List<Product> filteredProducts = new ArrayList<>();
        List<Product> allProducts = productRepository.findAll();
        int i = 0;
        while (i < allProducts.size()) {
            Product product = allProducts.get(i);
            if (Objects.isNull(product.getCategory())) {
                i++;
                continue; 
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
