package com.org.proddaturiMinApp.emums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public enum ProductSortOptions {

    // Sort Methodologies
    PRICE("discountPrice", null),
    PRODUCTNAME("name", null),

    // Sort Techniques
    LOWTOHIGH(null, Sort.Direction.ASC),
    AZ(null, Sort.Direction.ASC),
    HIGHTOLOW(null, Sort.Direction.DESC),
    ZA(null, Sort.Direction.DESC);

    private final String fieldName;
    private final Sort.Direction direction;

    ProductSortOptions(String fieldName, Sort.Direction direction) {
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public boolean isMethodology() {
        return fieldName != null;
    }

    public boolean isTechnique() {
        return direction != null;
    }
}
