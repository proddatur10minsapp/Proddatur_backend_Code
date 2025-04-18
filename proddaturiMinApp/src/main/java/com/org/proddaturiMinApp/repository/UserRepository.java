package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Integer> {
    Optional<User> findByMobileNumber(long mobileNumber);

    boolean existsByMobileNumber(long mobileNumber);

    @Query(value = "{}", sort = "{_id: -1}", fields = "{_id: 1}")
    User findTopByOrderByIdDesc();
}

