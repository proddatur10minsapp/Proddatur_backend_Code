package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.CartDTO;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.UserRepository;
import com.org.proddaturiMinApp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CartDTO> getCart(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCart();
    }

    @Override
    public void addToCart(String phoneNumber, String productId) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartDTO> cart = user.getCart();
        boolean productExists = false;

        for (CartDTO item : cart) {
            if (item.getProductId().equals(productId)) {
                item.setQuantity(item.getQuantity() + 1);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            CartDTO newItem = new CartDTO();
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            cart.add(newItem);
        }

        user.setCart(cart);
        userRepository.save(user);
    }

    @Override
    public void removeFromCart(String phoneNumber, String productId) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<CartDTO> cart = user.getCart();
        cart.removeIf(item -> item.getProductId().equals(productId));

        user.setCart(cart);
        userRepository.save(user);
    }

    @Override
    public void clearCart(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setCart(new ArrayList<>());
        userRepository.save(user);
    }
}
