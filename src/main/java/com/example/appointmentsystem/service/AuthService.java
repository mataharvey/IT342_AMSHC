package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.LoginRequest;
import com.example.appointmentsystem.dto.LoginResponse;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repository.UserRepository;
import com.example.appointmentsystem.security.JwtUtil;
import com.example.appointmentsystem.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔐 LOGIN method using AuthenticationManager + JWT
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // ✅ Principal is our CustomUserDetails
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Generate token using email (as username)
        String token = jwtUtil.generateToken(userDetails);


        // Return token, role, and userId in LoginResponse
        return new LoginResponse(
                token,
                userDetails.getUser().getRole(),
                userDetails.getUser().getId()
        );
    }

    // 📝 REGISTER method: encode password and save user
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
