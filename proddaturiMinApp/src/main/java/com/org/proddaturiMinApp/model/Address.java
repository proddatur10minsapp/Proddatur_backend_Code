package com.org.proddaturiMinApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private String id;
    private String houseNo;
    @NonNull
    private String type;
    private String areaOrStreet;
    private String landmark;
    private Integer pincode;
    private Boolean isDefault;
    private String phoneNumber;
}

