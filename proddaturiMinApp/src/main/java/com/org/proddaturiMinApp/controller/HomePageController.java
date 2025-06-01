package com.org.proddaturiMinApp.controller;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import com.org.proddaturiMinApp.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    private HomePageService homePageService;
    @GetMapping("/trends")
    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(@RequestParam(value = "phone-number",required = false) String phoneNumber) throws CommonExcepton {
        return homePageService.getTrendProducts(phoneNumber);
    }

}
