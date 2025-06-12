package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.dto.WishListInputDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.model.WishList;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.repository.WishlistRepository;
import com.org.proddaturiMinApp.service.WishListService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class WishListServiceImpl implements WishListService {
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    ProductRepository productRepository;
    @Override
    public ResponseEntity<Map<String, Object>> addProductToWishList(String phoneNumber, WishListInputDTO wishListInputDTO) throws InputFieldRequried, CommonExcepton {
        if (Objects.isNull(wishListInputDTO.getProductId()) || Objects.isNull(wishListInputDTO.getCategoryName())) {
            throw new InputFieldRequried("Product ID or Category Name must not be null");
        }
        WishList wishList = wishlistRepository.findById(phoneNumber).orElseGet(() -> {
            WishList newWishList = new WishList();
            newWishList.setId(phoneNumber);
            newWishList.setPhoneNumber(phoneNumber);
            newWishList.setIdProductMap(new HashMap<>());
            return newWishList;
        });
        wishList.setUpdatedAt(LocalDateTime.now());

        Map<ObjectId, Product> productsMap = wishList.getIdProductMap();
        if (Objects.nonNull(productsMap) && wishList.getIdProductMap().containsKey(wishListInputDTO.getProductId())) {
            throw new CommonExcepton("Product is already exists in wishlist ! please once visit Wishlist");
        }
        Product product = productRepository.findById(wishListInputDTO.getProductId().toString()).orElseThrow(() -> new CommonExcepton("Product not found with ID: " + wishListInputDTO.getProductId()));
        productsMap.put(wishListInputDTO.getProductId(),product);
        wishList.setUpdatedAt(LocalDateTime.now());
        wishList.setIdProductMap(productsMap);

        wishlistRepository.save(wishList);

        ProductDTO dto = new ProductDTO(product, wishListInputDTO.getCategoryName(), null);
        dto.setIsPresentInWishList(true);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product added to wishlist successfully");
        response.put("totalProductsInWishList", wishList.getTotalItemsInWishList());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
