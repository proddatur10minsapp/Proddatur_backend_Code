package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends MongoRepository<Address,String> {

}
