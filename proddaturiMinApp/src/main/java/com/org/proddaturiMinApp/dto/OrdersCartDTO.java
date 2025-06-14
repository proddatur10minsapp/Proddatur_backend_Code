package com.org.proddaturiMinApp.dto;

import com.org.proddaturiMinApp.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersCartDTO {
    @Id
    private String id;
    private String phoneNumber;
    private LocalDateTime updatedAt;
    private Collection<ProductInCartDTO> productsList;
    private Integer totalItemsInCart=0;
    private Double currentTotalPrice=0.0;
    private Double discountedAmount=0.0;
    private Double totalPrice=0.0;

    public OrdersCartDTO(Cart cart){
        this.id=cart.getId();
        this.phoneNumber=cart.getPhoneNumber();
        this.updatedAt=cart.getUpdatedAt();
        this.productsList=cart.getProductsMap().values();
        this.totalItemsInCart=cart.getTotalItemsInCart();
        this.currentTotalPrice=cart.getCurrentTotalPrice();
        this.discountedAmount=cart.getDiscountedAmount();
        this.totalPrice=cart.getTotalPrice();
    }
}
