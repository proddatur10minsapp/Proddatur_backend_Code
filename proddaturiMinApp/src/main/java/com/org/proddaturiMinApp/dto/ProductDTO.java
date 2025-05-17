package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.org.proddaturiMinApp.model.Product;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
public class ProductDTO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId category;
    private String categoryName;
    private String image;
    private int price;
    private int discountPrice;
    private String quantity;

    public ProductDTO(Product product, String categoryName){
        this.id = product.getId();
        this.name= product.getName();
        this.category=product.getCategory();
        this.categoryName=categoryName;
        this.image= product.getImage();
        this.price=product.getPrice();
        this.quantity=product.getQuantity();
        this.discountPrice=product.getDiscountPrice();
    }
}
