package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.WishListInputDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    // This API is used to add the Products to User's Wishlist
    @PostMapping("/add/{phoneNumber}")
    public ResponseEntity<Map<String, Object>> addProductToWishlist(@PathVariable("phoneNumber") String phoneNumber, @RequestBody WishListInputDTO wishListInputDTO) throws InputFieldRequried, CommonExcepton {
        return wishListService.addProductToWishList(phoneNumber, wishListInputDTO);
    }
}
