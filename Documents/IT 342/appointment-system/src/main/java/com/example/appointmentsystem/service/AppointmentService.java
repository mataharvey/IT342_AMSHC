package com.example.appointmentsystem.service;

import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment book(Appointment appointment) {
        boolean slotTaken = appointmentRepository.existsByDoctorIdAndAppointmentTime(
                appointment.getDoctorId(), appointment.getAppointmentTime()
        );

        if (slotTaken) {
            throw new RuntimeException("Slot already booked for this doctor.");
        }

        appointment.setStatus("BOOKED");
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAll() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }
}
