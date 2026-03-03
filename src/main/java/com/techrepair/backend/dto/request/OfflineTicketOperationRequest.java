package com.techrepair.backend.dto.request;

import lombok.Data;

@Data
public class OfflineTicketOperationRequest {
    private String operationId;
    private OfflineOperationType type;
    private TicketRequest ticket;
    private Long clientId;
    private Long ticketId;
    private Long technicianId;
}
