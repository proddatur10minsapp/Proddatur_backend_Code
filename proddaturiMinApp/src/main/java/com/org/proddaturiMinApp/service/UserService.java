package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.User;

import java.util.Map;

public interface UserService {
    public String generateOtp(String mobileNumber);

    public boolean validateOtp(String mobileNumber, String userOtp);

    public Boolean validateOtpAndSaveUser(String username, String mobileNumber, String otp);

    public Boolean updateUserData(String mobileNumber, User user);
    public String updateUserAddress(String mobileNumber, Map<String, Object> address);
}
