package com.org.proddaturiMinApp.emums;

import lombok.Getter;
import org.springframework.data.domain.Sort;

@Getter
public class ProductSortOptions {

    @Getter
    public enum SortMethodology {
        PRICE("discountPrice"),
        PRODUCTNAME("name");

        private final String fieldName;

        SortMethodology(String fieldName) {
            this.fieldName = fieldName;
        }
    }

    @Getter
    public enum SortTechnique {
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