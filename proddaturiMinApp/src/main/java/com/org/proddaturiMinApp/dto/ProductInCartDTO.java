package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

@Data
public class ProductInCartDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String productName;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId catagory;
    private String catagoryName;
    private Integer quantity=0;
    private Boolean isProductAvailabe;
    private Double price=0.0;
    private Double totalCurrentPrice=0.0;
    private Double discountedPrice=0.0;
    private Double totalDiscountedAmount=0.0;
    private Double totalPrice=0.0;
    private LocalDateTime updatedAt;


}
