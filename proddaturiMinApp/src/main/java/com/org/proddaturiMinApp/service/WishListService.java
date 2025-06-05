package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.WishListInputDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.model.WishList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WishListService {
    public ResponseEntity<Map<String, Object>> addProductToWishList(String phoneNumber, WishListInputDTO wishListInputDTO) throws InputFieldRequried, CommonExcepton;
}
