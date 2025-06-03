package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.service.ProductFilterService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductFilterServiceImpl implements ProductFilterService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ProductDTO> filterProducts(ObjectId categoryId, String filterBasedOn, String filterTechnique, String phoneNumber, int startIndex) {
        String sortField = getSortField(filterBasedOn);
        Sort.Direction direction = getSortDirection(filterTechnique);

        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(categoryId));
        query.with(Sort.by(direction, sortField));

        // Pagination: treat startIndex as page number, multiply by page size to skip
        query.skip((long) startIndex * PAGE_SIZE);
        query.limit(PAGE_SIZE);

        List<Product> products = mongoTemplate.find(query, Product.class);

        // Convert to DTOs
        List<ProductDTO> dtoList = new ArrayList<>(products.size());
        products.forEach(product -> dtoList.add(new ProductDTO(product)));

        return dtoList;
    }

    private String getSortField(String methodology) {
        if (!StringUtils.hasText(methodology)) {
            log.info("Sort methodology is null or empty");
            throw new IllegalArgumentException("Sort methodology must not be null or empty");
        }

        try {
            return SortMethodology.valueOf(methodology.toUpperCase()).getFieldName();
        } catch (IllegalArgumentException ex) {
            log.info("Invalid sorting methodology: {}", methodology);
            throw new IllegalArgumentException("Invalid sorting methodology: " + methodology);
        }
    }

    private Sort.Direction getSortDirection(String technique) {
        if (!StringUtils.hasText(technique)) {
            log.info("Sort technique is null or empty");
            throw new IllegalArgumentException("Sort technique must not be null or empty");
        }

        try {
            return SortTechnique.valueOf(technique.toUpperCase()).getDirection();
        } catch (IllegalArgumentException ex) {
            log.info("Invalid sorting technique: {}", technique);
            throw new IllegalArgumentException("Invalid sorting technique: " + technique);
        }
    }

    @Getter
    private enum SortMethodology {
        PRICE("discountPrice"),
        PRODUCTNAME("name");

        private final String fieldName;

        SortMethodology(String fieldName) {
            this.fieldName = fieldName;
        }
    }

    @Getter
    private enum SortTechnique {
        LOWTOHIGH(Sort.Direction.ASC),
        AZ(Sort.Direction.ASC),
        HIGHTOLOW(Sort.Direction.DESC),
        ZA(Sort.Direction.DESC);

        private final Sort.Direction direction;

        SortTechnique(Sort.Direction direction) {
            this.direction = direction;
        }
    }
}
