package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.ProductService;
import com.org.proddaturiMinApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/categories-name/{category}")
    public List<Product> getProducts(@PathVariable("category") String category) throws CommonExcepton {
        return productService.getProducts(category);
    }

    @GetMapping("/categories-name/{category}/{nextvalue}")
    public List<Product> getNextProducts(@PathVariable("category") String category, @PathVariable("nextvalue") int nextValue) throws CommonExcepton {
        return productService.getProductsViaNextValue(category, nextValue);
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable String id) throws CommonExcepton {
        return productService.getProductsById(id);
    }

    @GetMapping("/search/{productname}")
    public Set<Product> getProductsViaName(@PathVariable("productname") String productName) throws InputFieldRequried {
        return productService.getFilteredProductByName(productName);
    }


//    @GetMapping("/allProducts")
//    public List<Product> getAllProducts() {
//        return productService.allProducts();
//    }

//    @GetMapping("/getProductsByName/{productname}/{categoryname}")
//    public List<Product> getProductsViaName(@PathVariable("productname") String productName, @PathVariable("categoryname") String categoryName) {
//        return productService.getProductsByName(productName, categoryName);
//    }

}
