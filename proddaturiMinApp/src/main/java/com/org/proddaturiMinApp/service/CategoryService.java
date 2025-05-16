package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Optional<Category> getCategoryById(@RequestBody String id);

    public Optional<Category> getCategoryByName(@RequestBody String categoryName);


    public List<Category> allCategories();
}
