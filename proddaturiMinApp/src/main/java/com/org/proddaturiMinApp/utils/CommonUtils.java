package com.org.proddaturiMinApp.utils;

import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.model.User;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommonUtils {
    public ObjectId convertToObjectId(String id){
        ObjectId objectId=null;
        if (id != null && id.matches("^[a-fA-F0-9]{24}$")) {
            objectId=  new ObjectId(id);
        } else {
           log.info("Failed to convert to Object id {}",id);
        }
        return objectId;
    }
}
