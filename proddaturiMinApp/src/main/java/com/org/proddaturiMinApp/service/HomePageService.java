package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.exception.CommonExcepton;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;


public interface HomePageService {

    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(String phoneNumber) throws CommonExcepton;
}
