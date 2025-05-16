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
    String id;
    String houseNo;
    @NonNull
    String type;
    String areaOrStreet;
    String landmark;
    Integer pincode;
    Boolean isDefault;
    String phoneNumber;
}

