package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.ClinicDTO;
import com.example.appointmentsystem.model.Clinic;
import com.example.appointmentsystem.repository.ClinicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;

    // CREATE
    public Clinic addClinic(ClinicDTO dto) {
        Clinic clinic = new Clinic();
        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        return clinicRepository.save(clinic);
    }

    // READ ALL with pagination + sorting
    public Page<Clinic> getAllClinics(Pageable pageable) {
        return clinicRepository.findAll(pageable);
    }

    // READ by ID
    public Clinic getClinicById(Long id) {
        return clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found"));
    }

    // UPDATE
    public Clinic updateClinic(Long id, ClinicDTO dto) {
        Clinic clinic = getClinicById(id);
        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        return clinicRepository.save(clinic);
    }

    // DELETE
    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }

    public Page<ClinicDTO> searchClinics(String keyword, String mode, Pageable pageable) {
        Page<Clinic> clinics;

        if ("name".equalsIgnoreCase(mode)) {
            clinics = clinicRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } else if ("address".equalsIgnoreCase(mode)) {
            clinics = clinicRepository.findByAddressContainingIgnoreCase(keyword, pageable);
        } else {
            clinics = Page.empty(); // <- this must not have arguments
        }

        return clinics.map(clinic -> new ClinicDTO(clinic.getName(), clinic.getAddress()));
    }

}
