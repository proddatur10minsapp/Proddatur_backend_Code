package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.CartDTO;
import com.org.proddaturiMinApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/cart")

public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/addCart/{mobileNumber}/{productId}")
    public String addProductToCart(@PathVariable String mobileNumber, @PathVariable String productId) {
        cartService.addToCart(mobileNumber, productId);
        return "product Added To Cart successfully";
    }

    @GetMapping("/getCart/{mobileNumber}")
    public List<CartDTO> getCartValues(@PathVariable String mobileNumber) {
        return cartService.getCart(mobileNumber);

    }

    @DeleteMapping("/removeProduct/{mobileNumber}/{productId}")
    public String removeProductFromCart(@PathVariable String mobileNumber, @PathVariable String productId) {
        cartService.removeFromCart(mobileNumber, productId);
        return "product Deleted From Cart successfully";
    }

    @DeleteMapping("/clearCart/{mobileNumber}")
    public String clearCart(@PathVariable String mobileNumber) {
        cartService.clearCart(mobileNumber);
        return "Your cart is empty";
    }

}
