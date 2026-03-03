package com.techrepair.backend.dto.response;

import com.techrepair.backend.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfflineOperationResult {
    private String operationId;
    private boolean success;
    private String message;
    private Ticket ticket;
}
