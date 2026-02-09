package com.techrepair.backend.dto.request;

import com.techrepair.backend.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    @NotBlank
    private String name;

    @Email
    private String email;

    private UserRole role;
}
