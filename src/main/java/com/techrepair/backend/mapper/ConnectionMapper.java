package com.techrepair.backend.mapper;

import com.techrepair.backend.dto.response.ConnectionResponse;
import com.techrepair.backend.model.Connection;

public class ConnectionMapper {
    public static ConnectionResponse toDTO(Connection c) {
        if (c == null) return null;
        long duration = 0L;
        if (c.getCreatedAt() != null && c.getEndedAt() != null) {
            duration = java.time.Duration.between(c.getCreatedAt(), c.getEndedAt()).getSeconds();
        }
        return new ConnectionResponse(c.getId(), UserMapper.toDTO(c.getTechnician()), UserMapper.toDTO(c.getClient()), c.getStatus().name(), duration);
    }
}
