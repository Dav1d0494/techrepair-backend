package com.techrepair.backend.service;

import com.techrepair.backend.dto.response.DashboardStatsResponse;
import com.techrepair.backend.repository.ConnectionRepository;
import com.techrepair.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public DashboardStatsResponse stats() {
        long totalTickets = ticketRepository.count();
        long activeConnections = connectionRepository.findActiveConnections().size();
        double avgResolution = 0.0; // calculation omitted for brevity
        return new DashboardStatsResponse(totalTickets, activeConnections, avgResolution);
    }
}
