package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.model.City;
import com.example.appointmentsystem.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping
    public City create(@RequestBody City city) {
        return cityService.create(city);
    }

    @GetMapping
    public List<City> getAll() {
        return cityService.getAll();
    }
}
