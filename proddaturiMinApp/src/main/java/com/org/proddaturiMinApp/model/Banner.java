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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "banner_sponsorships")
public class Banner {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    private String image;
    private Integer priority;
    private String type;
}
