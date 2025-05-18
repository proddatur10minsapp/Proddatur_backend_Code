package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.exception.InputFieldRequried;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.service.ProductService;
import com.org.proddaturiMinApp.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public  Set<HashMap<String, Object>> getProducts(@PathVariable("category") String category) throws CommonExcepton {
        return productService.getProducts(category);
    }

    //now on going

    @GetMapping("/categories-name/{category}/{nextvalue}")
    public  Set<HashMap<String, Object>> getNextProducts(@PathVariable("category") String category, @PathVariable("nextvalue") int nextValue) throws CommonExcepton {
        return productService.getProductsViaNextValue(category, nextValue);
    }

    // need the old methodogy
    @GetMapping("/id/{id}")
    public ProductDTO getProductById(@PathVariable String id) throws CommonExcepton {
        return productService.getProductsById(id);
    }


    // done
    @GetMapping("/search/{productname}")
    public Set<HashMap<String, Object>> getProductsViaName(@PathVariable("productname") String productName) throws InputFieldRequried {
        return productService.getFilteredProductByName(productName);
    }




}
