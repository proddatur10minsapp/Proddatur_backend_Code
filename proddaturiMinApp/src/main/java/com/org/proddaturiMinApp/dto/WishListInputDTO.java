package com.org.proddaturiMinApp.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class WishListInputDTO {
    @JsonSerialize(using= ToStringSerializer.class)
    private ObjectId productId;
    private String categoryName;
}
