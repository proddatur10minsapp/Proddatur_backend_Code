package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.dto.ProductDTO;
import com.org.proddaturiMinApp.emums.ProductSortOptions.SortMethodology;
import com.org.proddaturiMinApp.emums.ProductSortOptions.SortTechnique;
import com.org.proddaturiMinApp.exception.InvalidSortOptionException;
import com.org.proddaturiMinApp.exception.MissingSortOptionException;
import com.org.proddaturiMinApp.model.Product;
import com.org.proddaturiMinApp.service.ProductFilterService;
import com.org.proddaturiMinApp.utils.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductFilterServiceImpl implements ProductFilterService {

    private static final int PAGE_SIZE = CommonConstants.PAGINATION_RANGE_FOR_10_values;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public ResponseEntity<List<ProductDTO>> filterProducts(ObjectId categoryId, String filterBasedOn, String filterTechnique, String phoneNumber, int startIndex) {
        try {
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
            products.forEach(product -> dtoList.add(new ProductDTO(product, null, null)));

            return ResponseEntity.ok(dtoList);
        } catch (MissingSortOptionException | InvalidSortOptionException ex) {
            log.error("Error in sorting options: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception ex) {
            log.error("Unexpected error while filtering products: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String getSortField(String methodology) {
        if (!StringUtils.hasText(methodology)) {
            log.info("Sort methodology is null or empty");
            throw new MissingSortOptionException("Sort methodology must not be null or empty");
        }

        try {
            return SortMethodology.valueOf(methodology.toUpperCase()).getFieldName();
        } catch (IllegalArgumentException ex) {
            log.info("Invalid sorting methodology: {}", methodology);
            throw new InvalidSortOptionException("Invalid sorting methodology: " + methodology);
        }
    }

    private Sort.Direction getSortDirection(String technique) {
        if (!StringUtils.hasText(technique)) {
            log.info("Sort technique is null or empty");
            throw new MissingSortOptionException("Sort technique must not be null or empty");
        }

        try {
            return SortTechnique.valueOf(technique.toUpperCase()).getDirection();
        } catch (IllegalArgumentException ex) {
            log.info("Invalid sorting technique: {}", technique);
            throw new InvalidSortOptionException("Invalid sorting technique: " + technique);
        }
    }
}
