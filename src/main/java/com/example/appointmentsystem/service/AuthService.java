package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.ForgotPasswordRequest;
import com.example.appointmentsystem.dto.LoginRequest;
import com.example.appointmentsystem.dto.LoginResponse;
import com.example.appointmentsystem.dto.ResetPasswordRequest;
import com.example.appointmentsystem.model.AppUser;
import com.example.appointmentsystem.repository.AppUserRepository;
import com.example.appointmentsystem.security.JwtUtil;
import com.example.appointmentsystem.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // ✅ Inject EmailService

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(
                token,
                userDetails.getAppUser().getRole(),
                userDetails.getAppUser().getId()
        );
    }

    public void register(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    // ✅ Step 1: Send Reset Code
    public void sendResetCode(String email) {
        Optional<AppUser> userOpt = appUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) return;

        AppUser user = userOpt.get();
        String code = String.valueOf(new Random().nextInt(900000) + 100000); // 6-digit code
        user.setResetCode(code);
        appUserRepository.save(user);

        emailService.sendEmail(user.getEmail(), "Password Reset Code", "Your reset code is: " + code);
    }

    // ✅ Step 2: Reset Password
    public boolean resetPassword(String email, String code, String newPassword) {
        Optional<AppUser> userOpt = appUserRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        AppUser user = userOpt.get();

        if (!code.equals(user.getResetCode())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetCode(null);
        appUserRepository.save(user);
        return true;
    }
}
