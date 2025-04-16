package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.DoctorRequestDTO;
import com.example.appointmentsystem.dto.DoctorResponseDTO;
import com.example.appointmentsystem.model.Clinic;
import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.repository.ClinicRepository;
import com.example.appointmentsystem.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ClinicRepository clinicRepository;

    public List<DoctorResponseDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DoctorResponseDTO> getDoctorsByClinic(String clinicName) {
        return doctorRepository.findByClinic_Name(clinicName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DoctorResponseDTO addDoctor(DoctorRequestDTO dto) {
        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setGender(dto.getGender());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setYearsOfExperience(dto.getYearsOfExperience());
        doctor.setClinic(clinic);

        return convertToDTO(doctorRepository.save(doctor));
    }

    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Clinic clinic = clinicRepository.findById(dto.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        doctor.setName(dto.getName());
        doctor.setGender(dto.getGender());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setYearsOfExperience(dto.getYearsOfExperience());
        doctor.setClinic(clinic);

        return convertToDTO(doctorRepository.save(doctor));
    }

    public void deleteDoctor(Long doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    private DoctorResponseDTO convertToDTO(Doctor doctor) {
        return new DoctorResponseDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getGender(),
                doctor.getSpecialization(),
                doctor.getYearsOfExperience(),
                doctor.getClinic().getId(),
                doctor.getClinic().getName(),
                doctor.getClinic().getAddress()
        );
    }
}
