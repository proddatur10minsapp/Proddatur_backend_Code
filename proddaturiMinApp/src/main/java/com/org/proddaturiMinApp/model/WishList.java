package com.org.proddaturiMinApp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "WishLists")
public class WishList {
    @Id
    private String id;
    private String phoneNumber;
    private Map<ObjectId,Product> idProductMap;
    private LocalDateTime updatedAt;
    public int getTotalItemsInWishList() {
        return idProductMap != null ? idProductMap.size() : 0;
    }
}
