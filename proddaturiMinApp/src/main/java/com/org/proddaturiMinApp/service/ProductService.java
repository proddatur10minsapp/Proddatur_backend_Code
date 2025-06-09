package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;


@Service
public interface ProductService {
  

    public  Set<HashMap<String, Object>> getProducts(String categoryName,String phoneNumber) throws CommonException;

    public  Set<HashMap<String, Object>> getProductsViaNextValue(String categoryName, int i,String phoneNumber) throws CommonException;

    public ProductDTO getProductsById(String id,String phoneNumber) throws CommonException;


    public Set<HashMap<String, Object>> getFilteredProductByName(String productName,String phoneNumber) throws InputFieldRequried;

    public  Set<HashMap<String, Object>> getProductsForTrends(String categoryName,String phoneNumber,Integer paginationRange) throws CommonException;


}
