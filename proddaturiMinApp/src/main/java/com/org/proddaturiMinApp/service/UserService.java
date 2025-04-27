package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.UserDetailsOutputDTO;
import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<UserDetailsOutputDTO> getUserDetails(String phoneNumber) throws InputFieldRequried, DetailsNotFound;

    ResponseEntity<String> updateUser(UserInputDTO userInputDTO);

    List<Address> getDeliveryAddressList(String phoneNumber) throws InputFieldRequried;

    ResponseEntity<UserDetailsOutputDTO> addNewAddress(UserInputDTO userInputDTO) throws InputFieldRequried;

}
