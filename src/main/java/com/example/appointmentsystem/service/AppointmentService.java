package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.BookingRequest;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public Appointment bookAppointment(BookingRequest request) {
        boolean isOverlapping = appointmentRepository
                .existsByDoctorIdAndAppointmentStartTimeLessThanEqualAndAppointmentEndTimeGreaterThanEqual(
                        request.getDoctorId(),
                        request.getAppointmentEndTime(),
                        request.getAppointmentStartTime()
                );

        if (isOverlapping) {
            throw new IllegalArgumentException("The appointment time is already booked.");
        }

        Appointment appointment = Appointment.builder()
                .patientName(request.getPatientName())
                .contactNumber(request.getContactNumber())
                .doctor(Appointment.createDoctorWithId(request.getDoctorId()))
                .patient(Appointment.createUserWithId(request.getPatientId()))
                .appointmentStartTime(request.getAppointmentStartTime())
                .appointmentEndTime(request.getAppointmentEndTime())
                .status("CONFIRMED")
                .build();

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }
}
