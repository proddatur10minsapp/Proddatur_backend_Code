package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(ObjectId category, Pageable pageable);

    List<Product> findByName(String category, Pageable pageable);
}
