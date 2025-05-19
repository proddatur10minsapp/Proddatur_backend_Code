package com.org.proddaturiMinApp.model;


import com.org.proddaturiMinApp.dto.ProductInCartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    private String id;
    private String phoneNumber;
    private LocalDateTime updatedAt;
    private Map<String, ProductInCartDTO> productsMap;
    private Integer totalItemsInCart=0;
    private Double currentTotalPrice=0.0;
    private Double discountedAmount=0.0;
    private Double totalPrice=0.0;
}
