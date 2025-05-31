package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductFilterDTO;

import java.util.List;

public interface ProductFilterService {
    List<ProductFilterDTO> filterProducts(String categoryName, String methodology, String technique, String phoneNumber, int startIndex) throws Exception;
}
