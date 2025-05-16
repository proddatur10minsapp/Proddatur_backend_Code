package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Category;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    public Category getCategoryById( String id) throws InputFieldRequried;

    public Category getCategoryByName( String categoryName) throws InputFieldRequried;

    public List<Category> getAllCategories();
}
