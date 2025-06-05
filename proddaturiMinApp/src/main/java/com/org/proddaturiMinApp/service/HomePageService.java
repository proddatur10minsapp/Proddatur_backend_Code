package com.org.proddaturiMinApp.service;

import com.org.proddaturiMinApp.exception.CommonException;
import com.org.proddaturiMinApp.exception.DatabaseException;
import com.org.proddaturiMinApp.model.Banner;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;


public interface HomePageService {

    public ResponseEntity<HashMap<Integer, Object>> getTrendProducts(String phoneNumber) throws CommonException;

    public ResponseEntity<List<Banner>> getBanners() throws DatabaseException;
}
