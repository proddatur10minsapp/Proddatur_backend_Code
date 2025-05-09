package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface ProductService {
  

    public List<Product> getFilteredProducts(String categoryName);

    public List<Product> getProducts(String categoryName);

    //public List<Product> getProductsViaNextValue(String categoryName, int i) ;


    public List<Product> allProducts() ;

    public Optional<Product> getProductsById(String id) ;


    public String getCategoryNameById(String categoryId) ;

    public List<Product> getProductsByName(String productName);

}
