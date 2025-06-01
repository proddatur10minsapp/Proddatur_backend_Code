package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.model.Orders;
import com.org.proddaturiMinApp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/{phoneNumber}/initiate-order/address/{addressId}")
    public ResponseEntity<Orders> initiateOrder(@PathVariable(value = "phoneNumber") String mobileNumber, @PathVariable(value = "addressId") String addressId) {
        return orderService.initiateOrder(mobileNumber, addressId);
    }

    @PostMapping("/{phoneNumber}/confirm-order/{orderId}")
    public ResponseEntity<Orders> confirmOrder(@PathVariable(value = "phoneNumber") String phoneNumber, @PathVariable(value = "orderId") String orderId) throws CannotModifyException {
        return orderService.confirmOrder(phoneNumber, orderId);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<Orders> getOrderDetails(@PathVariable(value = "orderId") String orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @GetMapping("{phoneNumber}/list")
    public ResponseEntity<List<Orders>> getAllOrderDetails(@PathVariable(value = "phoneNumber") String phoneNumber) {
        return orderService.getAllOrderDetails(phoneNumber);
    }

}
