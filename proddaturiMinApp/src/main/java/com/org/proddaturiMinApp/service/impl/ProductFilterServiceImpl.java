package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductFilterDTO;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.repository.CategoryRepository;
import com.org.proddaturiMinApp.service.ProductFilterService;
import com.org.proddaturiMinApp.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductFilterServiceImpl implements ProductFilterService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @Override
    public List<ProductFilterDTO> filterProducts(String categoryName, String methodology, String technique, String phoneNumber, int startIndex) throws Exception {
        Set<HashMap<String, Object>> rawProducts = productService.getProductsViaNextValue(categoryName, startIndex, phoneNumber);
        if (rawProducts == null || rawProducts.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductFilterDTO> products = rawProducts.stream()
                .map(this::mapToProductFilterDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        switch (methodology.toLowerCase()) {
            case "price":
                sortByPrice(products, technique);
                break;
            case "productname":
                sortByProductName(products, technique);
                break;
            default:
                throw new IllegalArgumentException("Invalid sorting methodology: " + methodology);
        }

        return products;
    }

    private ProductFilterDTO mapToProductFilterDTO(HashMap<String, Object> map) {
        try {
            Product product = new Product();

            Object idObj = map.get("id");
            if (idObj instanceof String) {
                product.setId(new ObjectId((String) idObj));
            } else if (idObj instanceof ObjectId) {
                product.setId((ObjectId) idObj);
            }

            product.setName((String) map.get("name"));
            product.setImage((String) map.get("image"));
            product.setQuantity((String) map.get("quantity"));
            product.setPrice(map.get("price") instanceof Number ? ((Number) map.get("price")).doubleValue() : null);
            product.setDiscountPrice(map.get("discountPrice") instanceof Number ? ((Number) map.get("discountPrice")).doubleValue() : null);
            product.setStock(map.get("stock") instanceof Number ? ((Number) map.get("stock")).intValue() : null);

            Object categoryObj = map.get("category");
            if (categoryObj instanceof String) {
                product.setCategory(new ObjectId((String) categoryObj));
            } else if (categoryObj instanceof ObjectId) {
                product.setCategory((ObjectId) categoryObj);
            }

            return new ProductFilterDTO(product);
        } catch (Exception e) {
            System.err.println("Error mapping to ProductFilterDTO: " + e.getMessage());
            return null;
        }
    }

    private void sortByPrice(List<ProductFilterDTO> products, String technique) {
        if ("lowtohigh".equalsIgnoreCase(technique)) {
            products.sort(Comparator.comparing(ProductFilterDTO::getDiscountPrice, Comparator.nullsLast(Double::compare)));
        } else if ("hightolow".equalsIgnoreCase(technique)) {
            products.sort(Comparator.comparing(ProductFilterDTO::getDiscountPrice, Comparator.nullsLast(Double::compare)).reversed());
        }
    }

    private void sortByProductName(List<ProductFilterDTO> products, String technique) {
        if ("az".equalsIgnoreCase(technique)) {
            products.sort(Comparator.comparing(ProductFilterDTO::getName, String.CASE_INSENSITIVE_ORDER));
        } else if ("za".equalsIgnoreCase(technique)) {
            products.sort(Comparator.comparing(ProductFilterDTO::getName, String.CASE_INSENSITIVE_ORDER).reversed());
        }
    }
}
