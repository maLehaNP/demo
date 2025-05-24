package com.example.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
public class CityImageService {
    private final RestTemplate restTemplate = new RestTemplate();
    private String url = "https://api.unsplash.com/photos/random?client_id=S28ZouiTQhEapHhjGZdorfhybdNghve8DTFIGC980xo";

    public String getRandomCityImage(String query) {
        Map<String, Object> response = restTemplate.getForObject(url + ";query=" + query, Map.class);
        System.out.println(url + query);
        return ((Map<String, String>) response.get("urls")).get("raw");
    }
}
