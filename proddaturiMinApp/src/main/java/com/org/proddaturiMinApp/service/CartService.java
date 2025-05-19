package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.CartInputDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface CartService {
    /*
    * POST /cart – Create a new cart

       GET /cart/{id} – Get cart details

       POST /cart/{id}/items – Add item to cart

        PUT /cart/{id}/items/{itemId} – Update item quantity

    DELETE /cart/{id}/items/{itemId} – Remove item

    POST /cart/{id}/checkout – Initiate checkout
    * */


    public ResponseEntity<Map<String, Object>> addItem(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton;

    public ResponseEntity<Cart> getAllItemsInCart(String phoneNumber);

    ResponseEntity<Cart> incrementTheProdut(String phoneNumber, CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton;
}
