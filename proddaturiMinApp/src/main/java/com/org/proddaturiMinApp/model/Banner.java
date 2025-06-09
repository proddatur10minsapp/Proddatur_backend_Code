package com.org.proddaturiMinApp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "banner_sponsorships")
public class Banner {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String image;
    private int priority;
    private String type;
    @Field(name = "category")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId categoryId;
    private String categoryName;
    private Date createdAt;
    private Date updatedAt;
    private String groupName;
}

