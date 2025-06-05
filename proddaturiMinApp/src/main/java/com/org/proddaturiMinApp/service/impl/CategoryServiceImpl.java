package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.exception.DetailsNotFoundException;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Category getCategoryById( String id) throws InputFieldRequried {
        if(Objects.isNull(id)){
            log.error("Missing Catagory Id ");
            throw new InputFieldRequried("Missing feild id (Catagory Id)");
        }
        Optional<Category> catagoryObj = categoryRepository.findById(id);
        if(catagoryObj.isEmpty()){
            log.info("No Catagory present for the Id {}",id);
            throw new DetailsNotFoundException("No Catagory present for the Id "+id);
        }
        return catagoryObj.get();
    }

    public Category getCategoryByName( String categoryName) throws InputFieldRequried {
        if(Objects.isNull(categoryName)){
            log.error("Missing feild catagoryName");
            throw new InputFieldRequried("Missing feild catagoryName");
        }
        Optional<Category> catagoryObj=categoryRepository.findByName(categoryName);
        if(catagoryObj.isEmpty()){
            log.info("No Catagory present in the name of catagory  {} ",categoryName);
            throw new DetailsNotFoundException("No Catagory present in the name of catagory "+categoryName);
        }
        return catagoryObj.get();
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        if(allCategories.isEmpty()){
            log.error("There in no categories");
            new DetailsNotFoundException("There in no categories");
        }
        return allCategories;
    }


}
