package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.model.User;
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



    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserInputDTO userInputDTO) throws InputFieldRequried {
        if(Objects.isNull(userInputDTO.getMobileNumber())){
            log.info("Mobile Number is mandatory");
            throw new InputFieldRequried("Mobile Number is mandatory");
        }
        if(Objects.isNull(userInputDTO.getUserName())) {
            log.info("Name is mandatory");
            throw new InputFieldRequried("Name is mandatory");
        }
        return userService.updateUser(userInputDTO);
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<User> getUserDetails(@PathVariable String mobileNumber) throws InputFieldRequried, DetailsNotFound {

        return userService.getUserDetails(mobileNumber);
    }

    @GetMapping("/Address/{mobileNumber}")
    public ResponseEntity<List<Address>> getDeliveryAddress(@PathVariable String mobileNumber) throws InputFieldRequried, DetailsNotFound {

        return userService.getDeliveryAddress(mobileNumber);
    }





    //  generate otp
//    @GetMapping("/generate")
//    public String generate(@RequestParam String mobileNumber) {
//        return userService.generateOtp(mobileNumber);
//
//    }
//
//    //  validate and save user
//    @PostMapping("/save")
//    public String validateAndSaveUser(@RequestBody User user, @RequestParam("userOtp") String userOtp) {
//        String username = user.getUserName();
//        String mobileNumber = user.getPhoneNumber();
//        Boolean userResponse = userService.validateOtpAndSaveUser(username, mobileNumber, userOtp);
//        if (userResponse) return commonConstants.successMessage + username;
//        else return commonConstants.failedMessage;
//    }
//
//    //Here update Username or MobileNumber based on Mobile Number
//    @PutMapping("/updateUser")
//    public String updateUserInfo(@RequestParam("mobileNumber") String mobileNumber, @RequestBody User user) {
//        boolean userinfo = userService.updateUserData(mobileNumber, user);
//        if (userinfo) return commonConstants.userUpdatedData;
//        else return commonConstants.userInvalidData;
//    }
//
//    @PostMapping("/addAddress/{mobileNumber}")
//    public ResponseEntity<String> updateAddress(
//            @PathVariable String mobileNumber,
//            @RequestBody Map<String, Object> address) {
//
//        String result = userService.updateUserAddress(mobileNumber, address);
//        return ResponseEntity.ok(result);
//    }


}
