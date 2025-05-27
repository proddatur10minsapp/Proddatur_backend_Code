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
    private String image;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId category;
    private String categoryName;
    private Integer quantity=0;//2
    private Boolean isProductAvailabe;
    private Double price=0.0;//20
    private Double totalCurrentPrice=0.0;//40
    private Double discountedPrice=0.0;//18
    private Double totalDiscountedAmount=0.0;//4
    private Double totalPrice=0.0;//36
    private LocalDateTime updatedAt;
    private Integer discountPercentage=0;//2%


}
