package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.emums.OrderStatus;
import com.org.proddaturiMinApp.model.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Orders,String>  {
    List<Orders> findByOrderStatus(OrderStatus orderStatus);
    List<Orders> findByphoneNumber(String phoneNumber, Pageable pageable);
}
