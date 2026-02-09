package com.techrepair.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsResponse {
    private long totalTickets;
    private long activeConnections;
    private double avgResolutionTimeHours;
}
