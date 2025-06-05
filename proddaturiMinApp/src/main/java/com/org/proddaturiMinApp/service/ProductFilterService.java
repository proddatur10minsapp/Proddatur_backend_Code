package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductFilterService {
    ResponseEntity<List<ProductDTO>> filterProducts(ObjectId categoryId, String filterBasedOn, String filterTechnique, String phoneNumber, int startIndex) ;
}
