package com.techrepair.backend.dto.request;

import com.techrepair.backend.enums.TicketPriority;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TicketRequest {
    @NotBlank
    private String title;

    private String description;

    @NotNull
    private TicketPriority priority;
}
