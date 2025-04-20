package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.utils.commonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.UserRepository;
import com.org.proddaturiMinApp.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImple implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImple.class);
    private static final SecureRandom random = new SecureRandom();
    public static String otp = String.format("%06d", random.nextInt(1000000)); // Generate 6-digit OTP
    private final int OTP_EXPIRY_MINUTES = commonConstants.otpExpireTime;
    private static String userMobileNumber;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private CommonUtils commonutils;

    // code for generate otp
    public String generateOtp(String mobileNumber) {
        userMobileNumber = mobileNumber;
        return otp;
    }

    // code for validate otp
    public boolean validateOtp(String mobileNumber, String userOtp) {
        String finalOtp = otp;
        if ((finalOtp != null && finalOtp.trim().equals(userOtp.trim())) && (mobileNumber.equals(userMobileNumber))) {
            log.info("OTP validate successfully");
            return true;
        }
        return false;
    }

    // code for validate otp and save user
    public Boolean validateOtpAndSaveUser(String userName, String mobileNumber, String otp) {
        final String updatedId = commonutils.generateUserId();
        if (validateOtp(mobileNumber, otp)) {
            if (!userRepository.existsByphoneNumber(mobileNumber)) {
                User newUser = new User(updatedId, mobileNumber, userName, null);
                userRepository.save(newUser);
                return true;
            }
        }
        return false;
    }

    // code for update username or Mobile Number  based on mobile number
    public Boolean updateUserData(String mobileNumber, User user) {
        Optional<User> userdata = userRepository.findByphoneNumber(mobileNumber);
        String userName = user.getUserName();
        String updatedMobileNumber = user.getPhoneNumber();
        if (userdata.isPresent()) {
            User userData = userdata.get();
            if (userName != null) userData.setUserName(userName);
            if (updatedMobileNumber != null) userData.setPhoneNumber(updatedMobileNumber);
            userRepository.save(userData);
            return true;
        }
        return false;
    }

    public String updateUserAddress(String mobileNumber, Map<String, Object> address) {
        Optional<User> optionalUser = userRepository.findByphoneNumber(mobileNumber);

        if (optionalUser.isEmpty()) {
            return "User not found with mobile number: " + mobileNumber;
        }

        // ✅ Validate required fields
        if (address.get("area") == null || address.get("street") == null) {
            return "Validation failed: 'area' and 'street' are required.";
        }

        User user = optionalUser.get();
        user.setAddress(address); // ✅ replace existing address
        userRepository.save(user);

        return "Address updated successfully!";
    }


}




