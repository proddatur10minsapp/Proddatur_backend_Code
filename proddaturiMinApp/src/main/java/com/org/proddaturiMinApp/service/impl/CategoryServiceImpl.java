package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public Optional<Category> getCategoryById(@RequestBody String id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(@RequestBody String categoryName) {
        return categoryRepository.findByName(categoryName);
    }


    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

}
