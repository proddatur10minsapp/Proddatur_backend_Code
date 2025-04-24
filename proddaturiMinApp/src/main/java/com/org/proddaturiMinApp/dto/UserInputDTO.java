package com.org.proddaturiMinApp.dto;

import com.org.proddaturiMinApp.model.Address;
import lombok.Data;
import java.util.Map;

@Data
public class UserInputDTO {
    private String mobileNumber;
    private String userName;
    private Address address;
}
