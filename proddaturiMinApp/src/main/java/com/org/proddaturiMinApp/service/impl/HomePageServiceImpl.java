package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.DatabaseException;
import com.org.proddaturiMinApp.model.Banner;
import com.org.proddaturiMinApp.model.Trends;
import com.org.proddaturiMinApp.repository.BannerRepository;
import com.org.proddaturiMinApp.repository.TrendsRepository;
import com.org.proddaturiMinApp.service.HomePageService;
import com.org.proddaturiMinApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private BannerRepository bannerRepository;
    private int paginationRange=10;
    @Override
    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(String phoneNumber) throws CommonException {
        List<Trends> trendCategory = trendsRepository.findAll();
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
        returnMap.put("groupName",trends.getGroupName());
        return returnMap;
    }

    @Override
    public ResponseEntity<List<Banner>> getBanners() throws DatabaseException {
        List<Banner> banners = bannerRepository.findAll();
        if (banners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(banners);
    }
}
