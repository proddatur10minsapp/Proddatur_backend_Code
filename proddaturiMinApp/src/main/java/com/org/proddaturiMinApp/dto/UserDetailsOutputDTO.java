package com.org.proddaturiMinApp.dto;

import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.model.User;
import lombok.Data;

import java.util.List;
@Data
public class UserDetailsOutputDTO {
    User user;
    List<Address> addressList;
}
