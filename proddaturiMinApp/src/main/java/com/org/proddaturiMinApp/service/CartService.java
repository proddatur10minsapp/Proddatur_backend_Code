package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.CartInputDTO;
import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CartService {


    public ResponseEntity<Map<String, Object>> addProductToCart(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonException;

    public ResponseEntity<Cart> getAllItemsInCart(String phoneNumber);

    public ResponseEntity<Cart> updatePoductInCart(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonException;

    public ResponseEntity<Map<String, Object>> getTotalNumberOfProductsInCart(String phoneNumber);
    public boolean emptyCart(String phoneNumber);

}

