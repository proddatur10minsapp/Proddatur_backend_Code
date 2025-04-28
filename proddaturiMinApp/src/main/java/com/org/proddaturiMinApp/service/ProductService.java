package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Category;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.repository.ProductRepository;
import com.org.proddaturiMinApp.utils.commonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public interface ProductService {
  

    public List<Product> getFilteredProducts(String categoryName, int i);

    public List<Product> getProducts(String categoryName);

    public List<Product> getProductsViaNextValue(String categoryName, int i) ;


    public List<Product> allProducts() ;

    public Optional<Product> getProductsById(String id) ;



    public String getCategoryNameById(String categoryId) ;

}
