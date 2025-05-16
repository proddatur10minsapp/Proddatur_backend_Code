package com.org.proddaturiMinApp.dto;

import com.org.proddaturiMinApp.model.Product;
import lombok.Data;

@Data
public class CartDTO {
    private String productId;
    private int quantity;
}
