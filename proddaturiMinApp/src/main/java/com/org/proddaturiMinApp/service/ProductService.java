package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getFilteredProducts(String categoryName, int i);
    List<Product> getProducts(String categoryName);
    List<Product> getProductsViaNextValue(String categoryName, int i);
    List<Product> allProducts();
    Optional<Product> getProductsById(String id);
    String getCategoryNameById(String id);



}


