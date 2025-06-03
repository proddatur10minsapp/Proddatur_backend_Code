package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.utils.CommonConstants;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String name;
    private String image;
    private List gallery;
    private Double price;
    private Double discountPrice;
    private String quantity;
    private String description;
    private String keyFeatures;
    private String specifications;
    private Integer stock;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId category;
    private String categoryName;
    private Boolean isPresentInWishList=false;
    private Boolean isPresentInCart=false;
    private Integer quantityInCart=0;

    public ProductDTO(Product product, String categoryName, Map<String, ProductInCartDTO> cartProductsMap){
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

        // now need to check the cart is null or not if not null if the products present in the cart need to update here
        if (Objects.nonNull(cartProductsMap)) {
            if (cartProductsMap.containsKey(product.getId().toString())) {
                // update the values
                this.isPresentInCart = CommonConstants.TRUE;
                quantityInCart = cartProductsMap.get(product.getId().toString()).getQuantity();
            }
        }
    }
}
