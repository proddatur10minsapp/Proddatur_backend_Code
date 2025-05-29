package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Trends;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendsRepository extends MongoRepository< Trends,String> {

}
