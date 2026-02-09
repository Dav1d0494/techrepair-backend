package com.techrepair.backend.mapper;

import com.techrepair.backend.dto.response.UserResponse;
import com.techrepair.backend.model.User;

public class UserMapper {
    public static UserResponse toDTO(User u) {
        if (u == null) return null;
        return new UserResponse(u.getId(), u.getEmail(), u.getName(), u.getRole(), u.getCreatedAt());
    }
}
