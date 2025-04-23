package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByPhoneNumber(String mobileNumber);

    boolean existsByPhoneNumber(String mobileNumber);

    @Query(value = "{}", sort = "{_id: -1}", fields = "{_id: 1}")
    User findTopByOrderByIdDesc();
}

