package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.UserDetailsOutputDTO;
import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;



    @PatchMapping("/initialUpdate")
    public ResponseEntity<String> updateUser(@RequestBody UserInputDTO userInputDTO) throws InputFieldRequried {
        if(Objects.isNull(userInputDTO.getPhoneNumber())){
            log.info("Phone Number is mandatory");
            throw new InputFieldRequried("Phone Number is mandatory");
        }

        return userService.updateUser(userInputDTO);
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<UserDetailsOutputDTO> getUserDetails(@PathVariable String phoneNumber) throws InputFieldRequried, DetailsNotFound {

        return userService.getUserDetails(phoneNumber);
    }

    // It will return the list of all the address
    @GetMapping("/Address/{phoneNumber}")
    public ResponseEntity<List<Address>> getDeliveryAddress(@PathVariable String phoneNumber) throws InputFieldRequried, DetailsNotFound {
        return ResponseEntity.ok(userService.getDeliveryAddressList(phoneNumber));
    }

    // add an new address to the user and check it is primary or not , if it is primary address add it
    // to user


    @PostMapping("/Address/addNew")
    public ResponseEntity<UserDetailsOutputDTO> addNewAddress(@RequestBody UserInputDTO userInputDTO) throws InputFieldRequried {
        if(Objects.isNull(userInputDTO.getPhoneNumber())){
            log.info("Phone Number is mandatory");
            throw new InputFieldRequried("Phone Number is mandatory");
        }
      return  userService.addNewAddress(userInputDTO);
    }


}
