package com.org.proddaturiMinApp.utils;

import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {
    @Autowired
    private UserRepository userRepository;
    public String generateUserId() {
        User lastUser = userRepository.findTopByOrderByIdDesc();  // Get last user details
        int nextId = 1;   //  if no users are present then set user id to 001 by default
        if (lastUser != null && lastUser.getId() != null) {
            String lastId = lastUser.getId(); // Example: "USER_045"
            String numberPart = lastId.substring(5); // Extract "045"
            nextId = Integer.parseInt(numberPart) + 1; // Increment number
        }
        return String.format("USER_%03d", nextId);  // user id format as USER_001, USER_002, etc
    }
}