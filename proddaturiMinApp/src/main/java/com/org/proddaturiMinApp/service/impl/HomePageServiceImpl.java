package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.model.Trends;
import com.org.proddaturiMinApp.repository.TrendsRepository;
import com.org.proddaturiMinApp.service.HomePageService;
import com.org.proddaturiMinApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Service
public class HomePageServiceImpl implements HomePageService {
    @Autowired
    private TrendsRepository trendsRepository;
    @Autowired
    private ProductService productService;
    private int paginationRange=10;
    @Override
    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(String phoneNumber) throws CommonExcepton {
        List<Trends> trendCategory = trendsRepository.findAll();
        Pageable pageable= PageRequest.of(0,paginationRange);
        HashMap<Integer, Object> returnMap = new HashMap<>();
        for (Trends trend: trendCategory) {
            returnMap.put(trend.getPriority() ,getTrendsReturnObject( trend,productService.getProductsForTrends(trend.getCategoryName(),phoneNumber,paginationRange)));
        }

       return ResponseEntity.ok(returnMap);
    }

    private Map<String, Object> getTrendsReturnObject(Trends trends, Set<HashMap<String, Object>> productList){
        Map<String, Object> returnMap=new HashMap<>();
        returnMap.put("categoryName",trends.getCategoryName());
        returnMap.put("backgroundImage",trends.getBackgroundImage());
        returnMap.put("priority",trends.getPriority());
        returnMap.put("categoryId",trends.getCategoryId().toString());
        returnMap.put("products",productList);
        return returnMap;
    }
}
