package com.example.appointmentsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientName;
    private String contactNumber;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private User patient;

    private LocalDateTime appointmentStartTime;
    private LocalDateTime appointmentEndTime;

    private String status; // PENDING, CONFIRMED, CANCELLED

    public static User createUserWithId(Long id) {
        return User.builder().id(id).build();
    }

    public static Doctor createDoctorWithId(Long id) {
        return Doctor.builder().id(id).build();
    }

}
