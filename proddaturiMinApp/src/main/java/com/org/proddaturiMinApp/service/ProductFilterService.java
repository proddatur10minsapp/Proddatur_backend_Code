package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.ProductFilterDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ProductFilterService {
    List<ProductFilterDTO> filterProducts(ObjectId categoryId, String methodology, String technique, String phoneNumber, int startIndex) throws Exception;
}
