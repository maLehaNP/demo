package com.example.demo.controller;

import com.example.demo.model.CityInfo;
import com.example.demo.service.CityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CityViewController {
    private final CityService cityService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        return "index";
    }

    @GetMapping("/city/{id}")
    public String cityDetails(@PathVariable Long id, Model model) throws CityNotFoundException {
        System.out.println(id);
        CityInfo city = cityService.getCityById(id);
        if (city == null) throw new CityNotFoundException();
        model.addAttribute("city", city);
        return "city";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) throws CityNotFoundException {
        List<CityInfo> results = cityService.searchCities(query);
        if (results.isEmpty()) throw new CityNotFoundException();
        model.addAttribute("cities", results);
        return "index";
    }

    @ExceptionHandler(CityNotFoundException.class)
    public String handleCityNotFound(Model model) {
        model.addAttribute("status", 404);
        model.addAttribute("error", "Город не найден");
        return "error";
    }
}
