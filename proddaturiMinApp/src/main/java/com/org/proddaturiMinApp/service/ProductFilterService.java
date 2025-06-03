package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductFilterService {
    List<ProductDTO> filterProducts(ObjectId categoryId, String filterBasedOn, String filterTechnique, String phoneNumber, int startIndex) throws Exception;
}
