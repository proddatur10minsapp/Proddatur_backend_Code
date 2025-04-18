package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.service.UserService;
import com.org.proddaturiMinApp.utils.commonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/user/authentication")
public class UserController {

    @Autowired
    UserService userService;
    //  generate otp
    @GetMapping("/generate")
    public String generate(@RequestParam long mobileNumber) {
       return  userService.generateOtp(mobileNumber);

    }

    //  validate and save user
    @PostMapping("/save")
    public String validateAndSaveUser(@RequestBody User user,@RequestParam("userOtp") String userOtp) {
        String username=user.getUserName();
        long mobileNumber=user.getMobileNumber();
        Boolean userResponse = userService.validateOtpAndSaveUser(username,mobileNumber,userOtp);
        if (userResponse) return commonConstants.successMessage+username;
        else return commonConstants.failedMessage;
    }

    //Here update Username or MobileNumber based on Mobile Number
    @PutMapping("/updateUser")
    public String updateUserInfo(@RequestParam("mobileNumber") long mobileNumber,@RequestBody User user) {
        boolean userinfo = userService.updateUserData(mobileNumber,user);
        if(userinfo) return commonConstants.userUpdatedData;
        else return commonConstants.userInvalidData;
    }

}
