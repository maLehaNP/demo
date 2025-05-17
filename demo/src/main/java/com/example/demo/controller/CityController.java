package com.example.demo.controller;

import com.example.demo.model.CityInfo;
import com.example.demo.model.CityTime;
import com.example.demo.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<CityInfo> getAllCities() {
        return cityService.getAllCities();
    }

    @GetMapping("/name/{name}")
    public CityInfo getCityByName(@PathVariable String name) {
        return cityService.getCityByName(name);
    }

    @GetMapping("/country/{country}")
    public CityInfo getCityByCountry(@PathVariable String country) {
        return cityService.getCityByCountry(country);
    }

    @GetMapping("/timezone/{timezone}")
    public CityInfo getCityByTimeZone(@PathVariable String timezone) {
        return cityService.getCityByTimeZone(timezone);
    }

    @GetMapping("/time/{name}")
    public CityTime getCityTimeByName(@PathVariable String name) {
        return cityService.getCityTimeByName(name);
    }
}