package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.ProductFilterDTO;
import com.org.proddaturiMinApp.service.ProductFilterService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filter")
public class ProductFilterController {

    @Autowired
    private ProductFilterService productFilterService;

    @GetMapping("/{categoryId}/{methodology}/{technique}/{startIndex}")
    public List<ProductFilterDTO> filterProducts(
            @PathVariable ObjectId categoryId,
            @PathVariable String methodology,
            @PathVariable String technique,
            @PathVariable int startIndex,
            @RequestParam(value = "phone-number", required = false) String phoneNumber
    ) throws Exception {
        return productFilterService.filterProducts(categoryId, methodology, technique, phoneNumber, startIndex);
    }
}
