package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.dto.ProductDTO;
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

    @GetMapping("/{categoryId}/{filterBasedOn}/{filterTechnique}/{startIndex}")
    public List<ProductDTO> filterProducts(
            @PathVariable ObjectId categoryId,
            @PathVariable String filterBasedOn,
            @PathVariable String filterTechnique,
            @PathVariable int startIndex,
            @RequestParam(value = "phone-number", required = false) String phoneNumber
    ) throws Exception {
        return productFilterService.filterProducts(categoryId, filterBasedOn, filterTechnique, phoneNumber, startIndex);
    }
}
