package com.example.appointmentsystem.repository;

import com.example.appointmentsystem.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
