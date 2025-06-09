package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.UserDetailsOutputDTO;
import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.DetailsNotFoundException;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<UserDetailsOutputDTO> getUserDetails(String phoneNumber) throws InputFieldRequried, DetailsNotFoundException;

    ResponseEntity<String> updateUser(UserInputDTO userInputDTO);

    List<Address> getDeliveryAddressList(String phoneNumber) throws InputFieldRequried;

    ResponseEntity<UserDetailsOutputDTO> addNewAddress(UserInputDTO userInputDTO) throws InputFieldRequried;

    ResponseEntity<Address> editAddress(String phoneNumber, String addressId, Address updatedAddress);

    ResponseEntity deteteAddress(String phoneNumber, String addressId) throws CannotModifyException;

    ResponseEntity<List<Address>> setAsDefaultUddress(String phoneNumber, String fromAddressId, String toAddressId) throws CommonException, InputFieldRequried;
}
