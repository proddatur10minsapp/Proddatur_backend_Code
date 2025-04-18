package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.User;

public interface UserService {
    public String generateOtp(long mobileNumber);

    public boolean validateOtp(long mobileNumber, String userOtp);

    public Boolean validateOtpAndSaveUser(String username, long mobileNumber, String otp);

    public Boolean updateUserData(long mobileNumber, User user);
}
