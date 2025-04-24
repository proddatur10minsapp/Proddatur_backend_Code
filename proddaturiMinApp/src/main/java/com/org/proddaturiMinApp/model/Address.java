package com.org.proddaturiMinApp.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "address")
@Data
public class Address {
    String houseNo;
    @NonNull
    String type;
    String areaOrStreet;
    String landmark;
    String pincode;

}
