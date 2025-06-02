package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.model.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BannerService {
    public ResponseEntity<List<Banner>> getAllBanners();
}