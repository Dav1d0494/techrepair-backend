package com.techrepair.backend.service;

import com.techrepair.backend.config.JwtConfig;
import com.techrepair.backend.dto.request.LoginRequest;
import com.techrepair.backend.dto.request.RegisterRequest;
import com.techrepair.backend.dto.response.ApiResponse;
import com.techrepair.backend.dto.response.LoginResponse;
import com.techrepair.backend.dto.response.UserResponse;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.UserRepository;
import com.techrepair.backend.security.JwtTokenProvider;
import com.techrepair.backend.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public ApiResponse<LoginResponse> login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return new ApiResponse<>(false, "User not found", null);
        }
        User user = userOpt.get();
        String token = tokenProvider.generateToken(user.getEmail());
        UserResponse ur = new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole(), user.getCreatedAt());
        return new ApiResponse<>(true, "Login successful", new LoginResponse(token, ur, 0));
    }

    public ApiResponse<UserResponse> register(RegisterRequest request) {
        if (!ValidationUtils.isValidEmail(request.getEmail())) {
            return new ApiResponse<>(false, "Email inválido", null);
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return new ApiResponse<>(false, "Email ya registrado", null);
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        User saved = userRepository.save(user);
        UserResponse ur = new UserResponse(saved.getId(), saved.getEmail(), saved.getName(), saved.getRole(), saved.getCreatedAt());
        return new ApiResponse<>(true, "Usuario registrado", ur);
    }
}
