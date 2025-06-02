package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductFilterDTO;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.service.ProductFilterService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductFilterServiceImpl implements ProductFilterService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ProductFilterDTO> filterProducts(ObjectId categoryId, String methodology, String technique, String phoneNumber, int startIndex) {
        String sortField = getSortField(methodology);
        Sort.Direction direction = getSortDirection(technique);

        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(categoryId));
        query.with(Sort.by(direction, sortField));
        query.skip(startIndex);
        query.limit(10);
        List<Product> products = mongoTemplate.find(query, Product.class);
        List<ProductFilterDTO> dtoList = new ArrayList<>(products.size());
        products.forEach(product -> dtoList.add(new ProductFilterDTO(product)));
        return dtoList;
    }

    private String getSortField(String methodology) {
        switch (methodology.toLowerCase()) {
            case "price":
                return "discountPrice";
            case "productname":
                return "name";
            default:
                log.info("Invalid sorting methodology: {}", methodology);
                throw new IllegalArgumentException("Invalid sorting methodology: " + methodology);
        }
    }

    private Sort.Direction getSortDirection(String technique) {
        switch (technique.toLowerCase()) {
            case "lowtohigh":
            case "az":
                return Sort.Direction.ASC;
            case "hightolow":
            case "za":
                return Sort.Direction.DESC;
            default:
                log.info("Invalid sorting technique: {}", technique);
                throw new IllegalArgumentException("Invalid sorting technique: " + technique);
        }
    }
}
