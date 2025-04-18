package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String categoryName);
}
