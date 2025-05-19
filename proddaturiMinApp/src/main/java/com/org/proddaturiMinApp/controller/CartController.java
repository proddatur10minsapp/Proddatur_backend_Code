package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.CartInputDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Cart;
import com.org.proddaturiMinApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/cart")
public class CartController {

//    POST /cart – Create a new cart
//
//    GET /cart/{id} – Get cart details -- 2
//
//    POST /cart/{id}/items – Add item to cart --1
//
//    PUT /cart/{id}/items/{itemId} – Update item quantity -3
//
//    DELETE /cart/{id}/items/{itemId} – Remove item -4
//
//    POST /cart/{id}/checkout – Initiate checkout --not needed now

    @Autowired
    private CartService cartService;

    // this api can be used to add to cart and incremnt the product in the all pages except Cart
    @PostMapping("/add/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> add(@PathVariable(value ="phoneNumber")String phoneNumber, @RequestBody CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton {
        if(Objects.isNull(cartInputDTO)){
            throw new InputFieldRequried("method body cannot be null");
        }
        return cartService.addItem(phoneNumber,cartInputDTO);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<Cart> getProduct(@PathVariable(value="phoneNumber")String phoneNumber) throws InputFieldRequried {
        if(Objects.isNull(phoneNumber)){
            throw new InputFieldRequried("Phone Number is requried");
        }
        return cartService.getAllItemsInCart(phoneNumber);
    }

    @PostMapping("/{phoneNumber}")
    public ResponseEntity<Cart> incremtentProduct(@PathVariable(value="phoneNumber")String phoneNumber, @RequestBody CartInputDTO cartInputDTO) throws InputFieldRequried, CommonExcepton {
        if(Objects.isNull(cartInputDTO)){
            throw new InputFieldRequried("method body cannot be null");
        }
        return cartService.incrementTheProdut(phoneNumber,cartInputDTO);
    }




}
