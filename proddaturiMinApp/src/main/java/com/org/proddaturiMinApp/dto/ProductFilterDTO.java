package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.org.proddaturiMinApp.model.Product;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class ProductFilterDTO {
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String image;
    private String quantity;
    private Double price;
    private Double discountPrice;
    private Integer stock;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId category;
    private Boolean isPresentInWishList = false;
    private Boolean isPresentInCart = false;

    public ProductFilterDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.image = product.getImage();
        this.quantity = product.getQuantity();
        this.price = product.getPrice();
        this.discountPrice = product.getDiscountPrice();
        this.stock = product.getStock();
        this.category = product.getCategory();
    }
}
