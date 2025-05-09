package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.ProductService;
import com.org.proddaturiMinApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts/{category}")
    public List<Product> getProducts(@PathVariable("category") String category) {
        return productService.getProducts(category);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        return productService.getProductsById(id);
    }

    @GetMapping("/allProducts")
    public List<Product> getAllProducts() {
        return productService.allProducts();
    }


    @GetMapping("/getProductsByName/{productname}")
    public List<Product> getProductsViaName(@PathVariable("productname") String productName) {
        return productService.getProductsByName(productName);
    }

}
