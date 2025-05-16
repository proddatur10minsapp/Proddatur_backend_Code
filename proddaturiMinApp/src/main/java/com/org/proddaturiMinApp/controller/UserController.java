package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.UserDetailsOutputDTO;
import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.exception.CommonExcepton;
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



    @PostMapping("/initialUpdate")
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


    @PostMapping("/Address/edit/{phoneNumber}/{addressId}")
    public ResponseEntity<Address> editAddress(@PathVariable String phoneNumber,@PathVariable String addressId,@RequestBody Address updatedAddress) throws InputFieldRequried {
        if(Objects.isNull(phoneNumber)){
            log.info("Phone Number is mandatory");
            throw new InputFieldRequried("Phone Number is mandatory");
        }
        return userService.editAddress(phoneNumber,addressId,updatedAddress);
    }

    @DeleteMapping("/Address/delete/{phoneNumber}/{addressId}")
    public ResponseEntity deleteAddress(@PathVariable String phoneNumber, @PathVariable String addressId) throws InputFieldRequried, CannotModifyException {
        if(Objects.isNull(phoneNumber)){
            log.info("Phone Number is mandatory");
            throw new InputFieldRequried("Phone Number is mandatory");
        }
        if(Objects.isNull(addressId)){
            log.info("addressId is mandatory");
            throw new InputFieldRequried("addressId is mandatory");
        }
        return userService.deteteAddress(phoneNumber,addressId);
    }
    @PostMapping("/Address/setAsDefault/{phoneNumber}/{fromAddressId}/{toAddressId}")
    public ResponseEntity<List<Address>> setAsDefaultUddress(@PathVariable String phoneNumber, @PathVariable String fromAddressId , @PathVariable String toAddressId) throws InputFieldRequried, CommonExcepton {
        if(Objects.isNull(phoneNumber)){
            log.info("Phone Number is mandatory");
            throw new InputFieldRequried("Phone Number is mandatory");
        }
        if(Objects.isNull(fromAddressId)){
            log.info("fromSddressId is mandatory");
            throw new InputFieldRequried("fromSddressId is mandatory");
        }
        if(Objects.isNull(toAddressId)){
            log.info("toAddressId is mandatory");
            throw new InputFieldRequried("toAddressId is mandatory");
        }
        return userService.setAsDefaultUddress(phoneNumber,fromAddressId,toAddressId);
    }



}
