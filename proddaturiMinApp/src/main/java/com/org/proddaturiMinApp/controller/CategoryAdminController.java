package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("admin/products/category")

public class CategoryAdminController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/addCategory")
    public String addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public Optional<Category> getCategoryById(@PathVariable String categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @PutMapping("/updateCategory/{categoryId}")
    public String updateCategory(@RequestBody Category category, @PathVariable String categoryId) {
        return categoryService.updateCategory(category, categoryId);
    }

    @DeleteMapping("/deleteCategoryById/{categoryId}")
    public String deleteCategoryById(@PathVariable String categoryId) {
        return categoryService.deleteCategoryById(categoryId);
    }

}
