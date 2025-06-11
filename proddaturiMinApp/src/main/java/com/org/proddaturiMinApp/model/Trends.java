package com.org.proddaturiMinApp.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trend_categories")
@Data
public class Trends {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @Field("category")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId categoryId;
    private String categoryName;
    private String backgroundImage;
    private Integer priority;
    private String groupName;
}
