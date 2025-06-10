package com.org.proddaturiMinApp.model;

import com.org.proddaturiMinApp.dto.OrdersCartDTO;
import com.org.proddaturiMinApp.emums.OrderStatus;
import com.org.proddaturiMinApp.emums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    private String id;
    private OrdersCartDTO OrdersCartDTO;
    private Double deliveryCharges;
    private Double totalPayable;
    private Address deliveryAddress;
    private String phoneNumber;
    private OrderStatus orderStatus;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
