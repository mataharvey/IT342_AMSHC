package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.dto.*;
import com.example.appointmentsystem.model.AppUser;
import com.example.appointmentsystem.model.Clinic;
import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.repository.AppUserRepository;
import com.example.appointmentsystem.repository.ClinicRepository;
import com.example.appointmentsystem.repository.DoctorRepository;
import com.example.appointmentsystem.security.CustomUserDetails;
import com.example.appointmentsystem.security.JwtUtil;
import com.example.appointmentsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private final AppUserRepository appUserRepository;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AppUser appUser) {
        String rawPassword = appUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        appUser.setPassword(encodedPassword);
        authService.register(appUser);
        return new AuthResponse("Registration successful");
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        AppUser user = appUserRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new LoginResponse(token, user.getRole(), user.getId(), user.getFullName(), user.getProfilePictureUrl());
    }

    @PostMapping("/register-doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody DoctorRegisterRequest request) {
        AppUser user = new AppUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole("DOCTOR");
        appUserRepository.save(user);

        Clinic clinic = clinicRepository.findById(request.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found"));

        Doctor doctor = new Doctor();
        doctor.setName(request.getFullName());
        doctor.setGender(request.getGender());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setClinic(clinic);
        doctorRepository.save(doctor);

        return ResponseEntity.ok("Doctor registered successfully.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        authService.sendResetCode(request.getEmail());
        return ResponseEntity.ok("Reset code sent.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        boolean success = authService.resetPassword(request.getEmail(), request.getCode(), request.getNewPassword());
        return success ? ResponseEntity.ok("Password reset successful.")
                       : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid code.");
    }

    @PutMapping("/update-profile")
    public ResponseEntity<String> updateProfile(@RequestBody UpdateProfileRequest request) {
        boolean success = authService.updateProfile(request);
        return success ? ResponseEntity.ok("Profile updated successfully.")
                       : ResponseEntity.badRequest().body("User not found.");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean success = authService.changePassword(request);
        return success ? ResponseEntity.ok("Password changed successfully.")
                       : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user or current password.");
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<AppUser> getUserProfile(@PathVariable Long id) {
        AppUser user = authService.getUserProfile(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-account/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        boolean deleted = authService.deleteAccount(id);
        return deleted ? ResponseEntity.ok("Account deleted successfully.")
                       : ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    @PutMapping("/upload-avatar/{userId}")
    public ResponseEntity<String> uploadAvatar(@PathVariable Long userId,
                                               @RequestParam("file") MultipartFile file) {
        try {
            String filename = authService.saveAvatar(userId, file);
            return ResponseEntity.ok("Avatar uploaded: " + filename);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Avatar upload failed");
        }
    }
}
