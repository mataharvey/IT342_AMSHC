package com.example.appointmentsystem.service;

import com.example.appointmentsystem.model.City;
import com.example.appointmentsystem.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City create(City city) {
        return cityRepository.save(city);
    }

    public List<City> getAll() {
        return cityRepository.findAll();
    }
}
