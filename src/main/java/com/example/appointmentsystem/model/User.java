package com.example.appointmentsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName; // Full name of the patient/user

    private String email;

    private String password;

    private String role; // "PATIENT", "ADMIN", etc.

    public String getFullName() {
        return fullName;
    }
}
