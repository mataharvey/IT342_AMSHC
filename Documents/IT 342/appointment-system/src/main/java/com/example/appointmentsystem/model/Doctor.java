package com.example.appointmentsystem.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private int experienceYears;
    private String location;
    private String nextAvailable; // e.g. "10:00 AM tomorrow"
    private int rating; // out of 100
}
