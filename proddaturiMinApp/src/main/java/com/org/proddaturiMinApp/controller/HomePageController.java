package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.model.Banner;
import com.org.proddaturiMinApp.repository.HomeRepository;
import com.org.proddaturiMinApp.service.BannerService;
import com.org.proddaturiMinApp.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    HomeRepository homeRepository;
    @Autowired
    private BannerService bannerService;
    @Autowired
    HomePageService homePageService;
    @GetMapping("/trends")
    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(@RequestParam(value = "phone-number",required = false) String phoneNumber) throws CommonExcepton {
        return homePageService.getTrendProducts(phoneNumber);
    }

    @GetMapping("/banners")
    public ResponseEntity<List<Banner>> getBanners(){
        return bannerService.getAllBanners();
    }



}

