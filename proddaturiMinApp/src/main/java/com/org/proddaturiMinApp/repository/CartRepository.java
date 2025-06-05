package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface CartRepository extends MongoRepository<Cart,String> {
}
