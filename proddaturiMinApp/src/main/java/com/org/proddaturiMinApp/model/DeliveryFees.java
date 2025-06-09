package com.org.proddaturiMinApp.model;

import lombok.Data;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "delivery_fees")
@Data
public class DeliveryFees {
    private String id;
    private Integer DeliveryFee;
}
