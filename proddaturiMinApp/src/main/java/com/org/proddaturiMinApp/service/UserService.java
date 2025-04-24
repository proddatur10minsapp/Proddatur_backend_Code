package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {

    public ResponseEntity<User> getUserDetails(String mobileNumber) throws InputFieldRequried, DetailsNotFound;

    ResponseEntity<String> updateUser(UserInputDTO userInputDTO);

//    public String generateOtp(String mobileNumber);
//
//    public boolean validateOtp(String mobileNumber, String userOtp);
//
//    public Boolean validateOtpAndSaveUser(String username, String mobileNumber, String otp);
//
//    public Boolean updateUserData(String mobileNumber, User user);
//    public String updateUserAddress(String mobileNumber, Map<String, Object> address);
}
