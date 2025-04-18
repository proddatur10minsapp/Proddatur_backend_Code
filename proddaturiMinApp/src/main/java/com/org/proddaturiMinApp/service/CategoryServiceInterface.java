package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;

import java.util.Optional;

public interface CategoryServiceInterface {

    String addCategory(Category category);

    Optional<Category> getCategoryById(String id);

    Optional<Category> getCategoryByName(String categoryName);

    String updateCategory(Category givenCategory, String categoryId);

    String deleteCategoryById(String id);
}
