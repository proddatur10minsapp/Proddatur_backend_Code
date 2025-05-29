package com.org.proddaturiMinApp.controller;


import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/id/{categoryId}")
    public Category getCategoryById(@PathVariable String categoryId) throws InputFieldRequried {
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping("/name/{categoryName}")
    public Category getCategoryByName(@PathVariable String categoryName) throws InputFieldRequried {
        return categoryService.getCategoryByName(categoryName);
    }

    @GetMapping("/all")
    public List<Category> getAllCategorys(){
        return categoryService.getAllCategories();
    }



}
