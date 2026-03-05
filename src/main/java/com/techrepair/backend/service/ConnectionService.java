package com.techrepair.backend.service;

import com.techrepair.backend.dto.request.ConnectionRequest;
import com.techrepair.backend.model.Connection;
import com.techrepair.backend.repository.ConnectionRepository;
import com.techrepair.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    public Connection startConnection(ConnectionRequest req, Long technicianId) {
        ConnectionRequest safeReq = Objects.requireNonNull(req, "Connection request is required");
        Long safeTechnicianId = Objects.requireNonNull(technicianId, "Technician id is required");
        Connection c = new Connection();
        c.setSessionId(safeReq.getSessionId());
        c.setStatus(com.techrepair.backend.enums.ConnectionStatus.ACTIVE);
        userRepository.findById(safeReq.getClientId()).ifPresent(c::setClient);
        userRepository.findById(safeTechnicianId).ifPresent(c::setTechnician);
        return connectionRepository.save(c);
    }

    public Connection endConnection(Long connectionId) {
        Long safeConnectionId = Objects.requireNonNull(connectionId, "Connection id is required");
        Optional<Connection> opt = connectionRepository.findById(safeConnectionId);
        if (opt.isEmpty()) return null;
        Connection c = opt.get();
        c.setStatus(com.techrepair.backend.enums.ConnectionStatus.CLOSED);
        c.setEndedAt(Instant.now());
        return connectionRepository.save(c);
    }

    public List<Connection> getActiveConnections() {
        return connectionRepository.findActiveConnections();
    }
}
