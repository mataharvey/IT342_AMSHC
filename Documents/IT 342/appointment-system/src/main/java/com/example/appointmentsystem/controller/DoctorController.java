package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getById(id);
    }

    @GetMapping("/search")
    public List<Doctor> getBySpecialization(@RequestParam String specialization) {
        return doctorService.getBySpecialization(specialization);
    }
}
