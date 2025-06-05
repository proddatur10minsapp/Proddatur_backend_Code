package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Banner;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BannerRepository extends MongoRepository<Banner, ObjectId> {

}




