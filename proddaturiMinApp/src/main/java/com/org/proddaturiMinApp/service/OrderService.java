package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.model.Orders;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    public ResponseEntity<Orders> initiateOrder(String phoneNumber, String addressId);

    ResponseEntity<Orders> confirmOrder(String mobileNumber, String orderId) throws CannotModifyException;

    ResponseEntity<Orders> getOrderDetails(String orderId) throws DetailsNotFound;

    ResponseEntity<List<Orders>> getAllOrderDetails(String phoneNumber);
}
