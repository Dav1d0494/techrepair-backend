package com.techrepair.backend.dto.response;

import com.techrepair.backend.enums.UserRole;
import lombok.Data;

import java.time.Instant;

@Data
public class UserResponse {
    private Long id;
    private String displayId;
    private String email;
    private String name;
    private UserRole role;
    private Instant createdAt;

    public UserResponse(Long id, String email, String name, UserRole role, Instant createdAt) {
        this.id = id;
        this.displayId = formatDisplayId(id);
        this.email = email;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
        this.displayId = formatDisplayId(id);
    }

    private String formatDisplayId(Long id) {
        if (id == null) {
            return null;
        }
        return String.format("%09d", id);
    }
}
