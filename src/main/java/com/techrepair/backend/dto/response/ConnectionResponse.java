package com.techrepair.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConnectionResponse {
    private Long id;
    private UserResponse technician;
    private UserResponse client;
    private String status;
    private Long durationSeconds;
}
