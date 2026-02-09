package com.techrepair.backend.dto.response;

import com.techrepair.backend.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserResponse user;
    private long expiresIn;
}
