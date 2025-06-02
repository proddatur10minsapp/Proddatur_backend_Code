package com.org.proddaturiMinApp.service.impl;

import com.org.proddaturiMinApp.model.Banner;
import com.org.proddaturiMinApp.repository.HomeRepository;
import com.org.proddaturiMinApp.service.BannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {
    @Autowired
    private HomeRepository homeRepository;
    @Override
    public ResponseEntity<List<Banner>> getAllBanners() {
        List<Banner> banners=homeRepository.findAll();
        return new ResponseEntity<>(banners, HttpStatus.OK);
    }
}