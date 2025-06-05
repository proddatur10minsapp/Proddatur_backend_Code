package com.org.proddaturiMinApp.repository;

import com.org.proddaturiMinApp.model.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HomeRepository  extends MongoRepository <Banner,String>  {

}
