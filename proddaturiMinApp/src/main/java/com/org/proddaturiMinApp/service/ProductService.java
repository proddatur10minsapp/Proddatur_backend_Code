package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public interface ProductService {
  

    public List<Product> getFilteredProducts(String categoryName, int i) throws CommonExcepton;

    public List<Product> getProducts(String categoryName) throws CommonExcepton;

    public List<Product> getProductsViaNextValue(String categoryName, int i) throws CommonExcepton;


    public List<Product> allProducts() ;

    public ProductDTO getProductsById(String id) throws CommonExcepton;


    public Set<Product> getFilteredProductByName(String productName) throws InputFieldRequried;

    //    public String getCategoryNameById(String categoryId) ;

}
