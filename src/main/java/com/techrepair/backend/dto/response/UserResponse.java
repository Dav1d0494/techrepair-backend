package com.techrepair.backend.dto.response;

import com.techrepair.backend.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private String name;
    private UserRole role;
    private Instant createdAt;
}
