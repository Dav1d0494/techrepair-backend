package com.techrepair.backend.dto.response;

import com.techrepair.backend.enums.TicketPriority;
import com.techrepair.backend.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String title;
    private TicketStatus status;
    private TicketPriority priority;
    private UserResponse client;
    private UserResponse technician;
}
