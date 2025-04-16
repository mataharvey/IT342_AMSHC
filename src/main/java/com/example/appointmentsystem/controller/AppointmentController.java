package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.dto.BookingRequest;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment book(@RequestBody BookingRequest request) {
        return appointmentService.bookAppointment(request);
    }

    @GetMapping("/patient/{id}")
    public List<Appointment> getByPatient(@PathVariable Long id) {
        return appointmentService.getByPatientId(id);
    }

    @GetMapping("/doctor/{id}")
    public List<Appointment> getByDoctor(@PathVariable Long id) {
        return appointmentService.getByDoctorId(id);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
    }
}
