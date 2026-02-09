package com.techrepair.backend.mapper;

import com.techrepair.backend.dto.response.TicketResponse;
import com.techrepair.backend.model.Ticket;

public class TicketMapper {
    public static TicketResponse toDTO(Ticket t) {
        if (t == null) return null;
        return new TicketResponse(t.getId(), t.getTitle(), t.getStatus(), t.getPriority(), UserMapper.toDTO(t.getClient()), UserMapper.toDTO(t.getTechnician()));
    }
}
