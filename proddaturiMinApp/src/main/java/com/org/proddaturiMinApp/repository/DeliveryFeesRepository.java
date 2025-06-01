package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.DeliveryFees;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeliveryFeesRepository extends  MongoRepository<DeliveryFees,String> {
    public List<DeliveryFees> findAll();
}
