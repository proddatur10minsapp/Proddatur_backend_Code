package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.repository.AddressRepository;
import com.org.proddaturiMinApp.service.UserService;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.UserRepository;
import com.org.proddaturiMinApp.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

   @Autowired
    UserRepository userRepository;

   @Autowired
    AddressRepository addressRepository;
    @Autowired
    private CommonUtils commonutils;


    // this getUserDetails method is used to fetch the user details based on the user
    @Override
    public ResponseEntity<User> getUserDetails(String mobileNumber) throws InputFieldRequried, DetailsNotFound {
        if(Objects.isNull(mobileNumber)){
            log.info("Mobile Number is mandatory");
            throw new InputFieldRequried("Mobile Number is mandatory");
        }
        Optional<User> optionalUser = userRepository.findByPhoneNumber(mobileNumber);
        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",mobileNumber);
            throw new DetailsNotFound(new StringBuilder().append("User Not found for: ").append(mobileNumber).toString());
        }
        User user= optionalUser.get();
        log.info("Used details found for the mobile Number :{}",user.toString());
        return ResponseEntity.ok().body(user);
    }

    // this method can be used to update userName , add new address into to the user
    @Override
    public ResponseEntity<String> updateUser(UserInputDTO userInputDTO) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(userInputDTO.getMobileNumber());
        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",userInputDTO.getMobileNumber());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        if(Objects.nonNull(userInputDTO.getUserName())) {
            user.setUserName(userInputDTO.getUserName());
        }
        // to add the user Address
        if(Objects.nonNull(userInputDTO.getAddress())) {
            Address address = userInputDTO.getAddress();
            if(Objects.nonNull(user.getAddress())){
                user.getAddress().put(address.getType(),address);
                log.info("Address is successfully added to the db for id  {} ",userInputDTO.getMobileNumber());
            }
            else{
                Map<String , Address> addressMap = new HashMap<>();
                addressMap.put(address.getType(),address);
                user.setAddress(addressMap);
                log.info("Address map is null created map and  successfully added to the db for id  {} ",userInputDTO.getMobileNumber());
            }
        }
        userRepository.save(user);
        log.info("user details saved successfully {} ",user);
        return  ResponseEntity.ok(user.toString());
    }


}
//    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
//    private static final SecureRandom random = new SecureRandom();
//    public static String otp = String.format("%06d", random.nextInt(1000000)); // Generate 6-digit OTP
//    private final int OTP_EXPIRY_MINUTES = commonConstants.otpExpireTime;
//    private static String userMobileNumber;

//
//
//
//    // code for generate otp
//    public String generateOtp(String mobileNumber) {
//        userMobileNumber = mobileNumber;
//        return otp;
//    }
//
//    // code for validate otp
//    public boolean validateOtp(String mobileNumber, String userOtp) {
//        String finalOtp = otp;
//        if ((finalOtp != null && finalOtp.trim().equals(userOtp.trim())) && (mobileNumber.equals(userMobileNumber))) {
//            log.info("OTP validate successfully");
//            return true;
//        }
//        return false;
//    }
//
//    // code for validate otp and save user
//    public Boolean validateOtpAndSaveUser(String userName, String mobileNumber, String otp) {
//        final String updatedId = commonutils.generateUserId();
//        if (validateOtp(mobileNumber, otp)) {
//            if (!userRepository.existsByPhoneNumber(mobileNumber)) {
//                User newUser = new User(updatedId, mobileNumber, userName, null);
//                userRepository.save(newUser);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // code for update username or Mobile Number  based on mobile number
//    public Boolean updateUserData(String mobileNumber, User user) {
//        Optional<User> userdata = userRepository.findByPhoneNumber(mobileNumber);
//        String userName = user.getUserName();
//        String updatedMobileNumber = user.getPhoneNumber();
//        if (userdata.isPresent()) {
//            User userData = userdata.get();
//            if (userName != null) userData.setUserName(userName);
//            if (updatedMobileNumber != null) userData.setPhoneNumber(updatedMobileNumber);
//            userRepository.save(userData);
//            return true;
//        }
//        return false;
//    }
//
//    public String updateUserAddress(String mobileNumber, Map<String, Object> address) {
//        Optional<User> optionalUser = userRepository.findByPhoneNumber(mobileNumber);
//
//        if (optionalUser.isEmpty()) {
//            return "User not found with mobile number: " + mobileNumber;
//        }
//
//        // ✅ Validate required fields
//        if (address.get("area") == null || address.get("street") == null) {
//            return "Validation failed: 'area' and 'street' are required.";
//        }
//
//        User user = optionalUser.get();
//        user.setAddress(address); // ✅ replace existing address
//        userRepository.save(user);
//
//        return "Address updated successfully!";
//    }



