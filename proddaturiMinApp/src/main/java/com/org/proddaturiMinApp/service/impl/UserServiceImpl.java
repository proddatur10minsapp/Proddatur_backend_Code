package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.UserDetailsOutputDTO;
import com.org.proddaturiMinApp.dto.UserInputDTO;
import com.org.proddaturiMinApp.exception.CannotModifyException;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.DetailsNotFound;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Address;
import com.org.proddaturiMinApp.repository.AddressRepository;
import com.org.proddaturiMinApp.service.UserService;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

   @Autowired
    UserRepository userRepository;

   @Autowired
    AddressRepository addressRepository;

    // this getUserDetails method is used to fetch the user details based on the user
    @Override
    public ResponseEntity<UserDetailsOutputDTO> getUserDetails(String phoneNumber) throws InputFieldRequried, DetailsNotFound {
        if(Objects.isNull(phoneNumber)){
            log.info("Mobile Number is mandatory");
            throw new InputFieldRequried("Mobile Number is mandatory");
        }
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",phoneNumber);
            throw new DetailsNotFound(new StringBuilder().append("User Not found for: ").append(phoneNumber).toString());
        }
        User user= optionalUser.get();
        log.info("Used details found for the mobile Number :{}",user.toString());
        UserDetailsOutputDTO userDetailsOutputDTO=new UserDetailsOutputDTO();
        userDetailsOutputDTO.setUser(user);
        userDetailsOutputDTO.setAddressList(getDeliveryAddressList(user.getPhoneNumber()));
        return ResponseEntity.ok().body(userDetailsOutputDTO);
    }

    // this method can be used to update userName , add new address into to the user
    @Override
    public ResponseEntity<String> updateUser(UserInputDTO userInputDTO) {

        Optional<User> optionalUser = userRepository.findByPhoneNumber(userInputDTO.getPhoneNumber());

        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",userInputDTO.getPhoneNumber());
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        if(Objects.nonNull(userInputDTO.getUserName())) {
            user.setUserName(userInputDTO.getUserName());
        }
        // to add the user Address
        if(Objects.nonNull(userInputDTO.getAddress())) {

            Address address = userInputDTO.getAddress();
            String addressID=UUID.randomUUID().toString();
            address.setId(addressID);
            if(Objects.nonNull(user.getAddress())){
                user.getAddress().put(address.getType(),addressID);
                log.info("Address is successfully added to the db for id  {} ",userInputDTO.getPhoneNumber());
            }
            else{
                Map<String , String> addressMap = new HashMap<>();
                addressMap.put(address.getType(),addressID);
                address.setIsDefault(true);
                user.setAddress(addressMap);
                log.info("Address map is null created map and  successfully added to the db for id  {} ",userInputDTO.getPhoneNumber());
            }
            address.setPhoneNumber(userInputDTO.getPhoneNumber());

            addressRepository.save(address);
            log.info("Added new address {}",address);
        }
        userRepository.save(user);
        log.info("user details saved successfully {} ",user);
        return  ResponseEntity.ok(user.toString());
    }

    // To fetch all the address list to display
    @Override
    public List<Address> getDeliveryAddressList(String phoneNumber) throws InputFieldRequried {
        if(Objects.isNull(phoneNumber)){
            log.info("Mobile Number is mandatory");
            throw new InputFieldRequried("Mobile Number is mandatory");
        }
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",phoneNumber);
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",user);
        Map<String, String> allAddress = user.getAddress();
        if(Objects.isNull(allAddress)){
            throw new DetailsNotFound("No address Found for the User  "+user.getPhoneNumber());
        }
        List<Address> addressList = allAddress.values().stream()
                .map(addressRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return addressList;

    }

    @Override
    public ResponseEntity<UserDetailsOutputDTO> addNewAddress(UserInputDTO userInputDTO) throws InputFieldRequried {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(userInputDTO.getPhoneNumber());

        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",userInputDTO.getPhoneNumber());
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        Address address = userInputDTO.getAddress();
        address.setId(UUID.randomUUID().toString());
        address.setPhoneNumber(userInputDTO.getPhoneNumber());
        Map<String, String> addressList = user.getAddress();
        if(Objects.nonNull(addressList)){
            // User Address Should be non-null
            if(userInputDTO.getAddress().getIsDefault()){
                addressList.values().stream()
                        .map(addressId -> addressRepository.findById(addressId))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .peek(address1 -> address1.setIsDefault(false))
                        .forEach(addressRepository::save);
            }
            addressList.put(address.getType(),address.getId());
            log.info("New user address is added to the User address collection ");
        }
        else{
            addressList=new HashMap<>();
            user.setAddress(addressList);
            //If user Address is null
            if(!address.getIsDefault()){
               address.setIsDefault(true);
            }
        }
        // save the address
        addressRepository.save(address);
        // add the address type and the id with with user
        addressList.put(address.getType(),address.getId());
        log.info("There is no address for the user , new address is added for the user {}",user.getPhoneNumber());
        userRepository.save(user);
        log.info("{} , is updated successfully",user);
        UserDetailsOutputDTO userDetailsOutputDTO=new UserDetailsOutputDTO();
        userDetailsOutputDTO.setUser(user);
        userDetailsOutputDTO.setAddressList(getDeliveryAddressList(user.getPhoneNumber()));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsOutputDTO);
    }

    @Override
    public ResponseEntity<Address> editAddress(String phoneNumber, String addressId, Address updatedAddress) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",phoneNumber);
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        if(!user.getAddress().containsValue(addressId)){
            log.error("User address not found with the address id {} address {}",phoneNumber,addressId);
            throw new DetailsNotFound("Address Not found "+ phoneNumber+" addressid "+ addressId);
        }

       Address address=addressRepository.findById(addressId).get();
        address.setHouseNo(updatedAddress.getHouseNo());
        address.setAreaOrStreet(updatedAddress.getAreaOrStreet());
        address.setLandmark(updatedAddress.getLandmark());
        address.setPincode(updatedAddress.getPincode());
        address.setPhoneNumber(updatedAddress.getPhoneNumber());
        if(Objects.nonNull(updatedAddress.getType())&&!address.getType().equalsIgnoreCase(updatedAddress.getType())){
            user.getAddress().remove(address.getType());
            address.setType(updatedAddress.getType());
            user.getAddress().put(address.getType(),address.getId());
            userRepository.save(user);
            log.info("Address type is changed and updated to the db {} :{} /n address-{}",phoneNumber,user,address);
        }

        addressRepository.save(address);
        log.info("address updated successfully for {} : {}",address.getPhoneNumber(),address);
        return ResponseEntity.ok().body(address);
    }

    @Override
    public ResponseEntity deteteAddress(String phoneNumber, String addressId) throws CannotModifyException {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",phoneNumber);
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        if(!user.getAddress().containsValue(addressId)){
            log.error("User address not found with the address id {} address {}",phoneNumber,addressId);
            throw new DetailsNotFound("Address Not found "+ phoneNumber+" addressid "+ addressId);
        }

        Address address=addressRepository.findById(addressId).get();
        if(address.getIsDefault()){
            throw new CannotModifyException("Default Address can't be modified please change the default to another first");
        }
        user.getAddress().remove(address.getType());
        log.info("Address has been removed from the uesr collection {}:{}",phoneNumber,address);
        userRepository.save(user);
        log.info("user details updated successfully {} : {}",phoneNumber,user);
        addressRepository.deleteById(addressId);
        log.info("Address details removed successfully {}: {}",phoneNumber,address);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @Override
    public ResponseEntity<List<Address>> setAsDefaultUddress(String phoneNumber, String fromAddressId, String toAddressId) throws CommonExcepton, InputFieldRequried {
        if(fromAddressId.equals(toAddressId)){
            log.info("Both from and to address to set as default is same ,please check");
            throw new CommonExcepton("Both from and to address to set as default is same ,please check");
        }
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if(optionalUser.isEmpty()){
            log.error("User Not found for: {}",phoneNumber);
            throw new DetailsNotFound("User Not found");
        }
        User user=optionalUser.get();
        log.info("fetched user details are {} ",optionalUser.get());
        if(!user.getAddress().containsValue(fromAddressId)){
            log.error("User address not found with the fromAddress id {} address {}",phoneNumber,fromAddressId);
            throw new DetailsNotFound("Address Not found "+ phoneNumber+" fromSddressid "+ fromAddressId);
        }
        if(!user.getAddress().containsValue(toAddressId)){
            log.error("User address not found with the toAddressId id {} address {}",phoneNumber,toAddressId);
            throw new DetailsNotFound("Address Not found "+ phoneNumber+" toAddressId "+ toAddressId);
        }
        Address fromAddress=addressRepository.findById(fromAddressId).get();
        if(!fromAddress.getIsDefault()){
            log.info("formAddress is not a default addresss {} : {}",phoneNumber,fromAddress);
            throw new CommonExcepton("formAddress is not a default addresss");
        }
        Address toAddress=addressRepository.findById(toAddressId).get();
        if(toAddress.getIsDefault()){
            log.info("toAddress is already  default addresss {} : {}",phoneNumber,fromAddress);
            throw new CommonExcepton("toAddress is already  default addresss");
        }
        fromAddress.setIsDefault(false);
        toAddress.setIsDefault(true);
        addressRepository.save(fromAddress);
        addressRepository.save(toAddress);
        log.info("address updated successfully");
        List<Address> addressList = getDeliveryAddressList(phoneNumber);
        return ResponseEntity.ok().body(addressList);

    }


}
