package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getCart(String phoneNumber);

    void addToCart(String phoneNumber, String productId);

    void removeFromCart(String phoneNumber, String productId);

    void clearCart(String phoneNumber);
}
