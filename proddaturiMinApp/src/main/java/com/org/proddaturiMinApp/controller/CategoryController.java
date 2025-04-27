package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("users/products/category")

public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/getCategoryById/{categoryId}")
    public Optional<Category> getCategoryById(@PathVariable String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/getCategoryByName/{categoryName}")
    public Optional<Category> getCategoryByName(@PathVariable String categoryName) {
        return categoryService.getCategoryByName(categoryName);
    }

    @GetMapping("/allCategory")
    public List<Category> getAllCategories() {
        return categoryService.allCategories();
    }


}
