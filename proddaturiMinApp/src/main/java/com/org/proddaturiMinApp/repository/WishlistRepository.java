package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.WishList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<WishList,String> {
    Optional<WishList> findByPhoneNumber(String phoneNumber);
}
