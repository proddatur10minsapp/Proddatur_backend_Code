package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.utils.commonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceInterface {

    @Autowired
    private CategoryRepository categoryRepository;

    public String addCategory(@RequestBody Category category) {
        Category response = categoryRepository.save(category);
        if (response.getId() == null) return commonConstants.failedToSave + category.getName();
        return commonConstants.dataSaved;
    }

    public Optional<Category> getCategoryById(@RequestBody String id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(@RequestBody String categoryName) {
        return categoryRepository.findByName(categoryName);
    }

    public String updateCategory(@RequestBody Category givenCategory, String categoryId) {
        Category filteredCategory = getCategoryById(categoryId).get();
        if (givenCategory.getName() != null)
        {filteredCategory.setName(givenCategory.getName());}
        if (givenCategory.getImage() != null) filteredCategory.setImage(givenCategory.getImage());
        categoryRepository.save(filteredCategory);
        return commonConstants.dataUpdatedData;
    }

    public String deleteCategoryById(@RequestBody String id) {
        categoryRepository.deleteById(id);
        return commonConstants.dataDeleted;
    }
    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

}
