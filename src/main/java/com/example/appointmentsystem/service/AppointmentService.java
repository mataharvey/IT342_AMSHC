package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.AppointmentRequestDTO;
import com.example.appointmentsystem.model.Appointment;
import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.model.NotificationType;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repository.AppointmentRepository;
import com.example.appointmentsystem.repository.DoctorRepository;
import com.example.appointmentsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final NotificationService notificationService;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public Appointment bookAppointment(AppointmentRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User patient = userRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        boolean isOverlapping = appointmentRepository
                .existsByDoctorIdAndAppointmentStartLessThanEqualAndAppointmentEndGreaterThanEqual(
                        doctor.getId(),
                        dto.getAppointmentEnd(),
                        dto.getAppointmentStart()
                );

        if (isOverlapping) {
            throw new RuntimeException("The appointment time is already booked.");
        }

        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .appointmentStart(dto.getAppointmentStart())
                .appointmentEnd(dto.getAppointmentEnd())
                .status("CONFIRMED")
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 🔔 Auto-notification on booking with formatted date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");
        String formattedStart = savedAppointment.getAppointmentStart().format(formatter);

        notificationService.createNotification(
                patient.getId(),
                "Your appointment with Dr. " + doctor.getName() + " is confirmed for " + formattedStart + ".",
                NotificationType.APPOINTMENT_REMINDER
        );

        notificationService.createNotification(
                doctor.getId(),
                "You have a new appointment with " + patient.getFullName() + " on " + formattedStart + ".",
                NotificationType.APPOINTMENT_REMINDER
        );

        return savedAppointment;
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        Doctor doctor = appointment.getDoctor();
        User patient = appointment.getPatient();
        LocalDateTime startTime = appointment.getAppointmentStart();

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' hh:mm a");
        String formattedStart = startTime.format(formatter);

        notificationService.createNotification(
                patient.getId(),
                "Your appointment on " + formattedStart + " was cancelled.",
                NotificationType.CANCELLATION
        );

        notificationService.createNotification(
                doctor.getId(),
                "Appointment with " + patient.getFullName() + " on " + formattedStart + " was cancelled.",
                NotificationType.CANCELLATION
        );
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
}
