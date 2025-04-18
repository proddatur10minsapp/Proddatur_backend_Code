package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ProductServiceInterface {
    List<Product> getFilteredProducts(String categoryName, int i);
    List<Product> getProducts(String categoryName);
    List<Product> getProductsViaNextValue(String categoryName, int i);
    List<Product> allProducts();
    Optional<Product> getProductsById(String id);
    Product saveProduct(Product products);
    List<Product> saveAllProduct(List<Product> products);
    Product updateProduct(String id, Product updatedProduct);
    String deleteProductById(String id);
    Category getCategory(Product product);
    String getCategoryNameById(String id);
}


