package com.techrepair.backend.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ConnectionRequest {
    @NotNull
    private Long clientId;

    @NotBlank
    private String sessionId;
}
