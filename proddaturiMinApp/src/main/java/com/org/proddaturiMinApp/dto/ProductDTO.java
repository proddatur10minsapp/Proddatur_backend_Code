package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.org.proddaturiMinApp.model.Product;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class ProductDTO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String image;
    private List gallery;
    private int price;
    private int discountPrice;
    private String quantity;
    private String description;
    private String keyFeatures;
    private String specifications;
    private String stock;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId category;
    private String categoryName;

    public ProductDTO(Product product, String categoryName){
        this.id = product.getId();
        this.name= product.getName();
        this.image=product.getImage();
        this.gallery=product.getGallery();
        this.price=product.getPrice();
        this.discountPrice=product.getDiscountPrice();
        this.quantity=product.getQuantity();
        this.description=product.getDescription();
        this.keyFeatures= product.getKeyFeatures();
        this.specifications= product.getSpecifications();
        this.stock=product.getStock();
        this.category=product.getCategory();
        this.categoryName=categoryName;
    }
}
