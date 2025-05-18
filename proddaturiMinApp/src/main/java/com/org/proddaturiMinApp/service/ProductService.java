package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Service
public interface ProductService {
  

    public  Set<HashMap<String, Object>> getProducts(String categoryName) throws CommonExcepton;

    public  Set<HashMap<String, Object>> getProductsViaNextValue(String categoryName, int i) throws CommonExcepton;

    public ProductDTO getProductsById(String id) throws CommonExcepton;


    public Set<HashMap<String, Object>> getFilteredProductByName(String productName) throws InputFieldRequried;


}
