package com.org.proddaturiMinApp.dto;

import com.org.proddaturiMinApp.model.Address;
import lombok.Data;

@Data
public class UserInputDTO {
    private String phoneNumber;
    private String userName;
    private Address address;
}
