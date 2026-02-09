package com.techrepair.backend.service;

import com.techrepair.backend.dto.request.UserUpdateRequest;
import com.techrepair.backend.dto.response.ApiResponse;
import com.techrepair.backend.dto.response.UserResponse;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.UserRepository;
import com.techrepair.backend.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public ApiResponse<UserResponse> create(User user) {
        if (!ValidationUtils.isValidEmail(user.getEmail())) {
            return new ApiResponse<>(false, "Email inválido", null);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        UserResponse ur = new UserResponse(saved.getId(), saved.getEmail(), saved.getName(), saved.getRole(), saved.getCreatedAt());
        return new ApiResponse<>(true, "Usuario creado", ur);
    }

    public ApiResponse<UserResponse> update(Long id, UserUpdateRequest req) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) return new ApiResponse<>(false, "Usuario no encontrado", null);
        User user = opt.get();
        if (req.getEmail() != null && ValidationUtils.isValidEmail(req.getEmail())) user.setEmail(req.getEmail());
        if (req.getName() != null) user.setName(req.getName());
        if (req.getRole() != null) user.setRole(req.getRole());
        User saved = userRepository.save(user);
        UserResponse ur = new UserResponse(saved.getId(), saved.getEmail(), saved.getName(), saved.getRole(), saved.getCreatedAt());
        return new ApiResponse<>(true, "Usuario actualizado", ur);
    }

    public ApiResponse<String> delete(Long id) {
        if (!userRepository.existsById(id)) return new ApiResponse<>(false, "Usuario no encontrado", null);
        userRepository.deleteById(id);
        return new ApiResponse<>(true, "Usuario eliminado", null);
    }
}
