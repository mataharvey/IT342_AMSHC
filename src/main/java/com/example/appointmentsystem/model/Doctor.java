package com.example.appointmentsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Full name of the doctor (e.g., "Mark Reyes")

    private String gender;

    private String specialization;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "clinic")
    private Clinic clinic;

    public String getName() {
        return name;
    }
}
