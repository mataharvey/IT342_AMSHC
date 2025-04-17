package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.dto.AuthResponse;
import com.example.appointmentsystem.dto.LoginRequest;
import com.example.appointmentsystem.dto.LoginResponse;
import com.example.appointmentsystem.model.AppUser;
import com.example.appointmentsystem.security.CustomUserDetails;
import com.example.appointmentsystem.security.JwtUtil;
import com.example.appointmentsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AppUser appUser) {
        String rawPassword = appUser.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("👉 Raw password: " + rawPassword);
        System.out.println("🔐 Encoded password: " + encodedPassword);

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

        return new LoginResponse(token, userDetails.getRole(), userDetails.getUserId());
    }
}
