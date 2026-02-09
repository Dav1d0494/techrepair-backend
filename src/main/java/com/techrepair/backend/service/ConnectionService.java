package com.techrepair.backend.service;

import com.techrepair.backend.dto.request.ConnectionRequest;
import com.techrepair.backend.model.Connection;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.ConnectionRepository;
import com.techrepair.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserRepository userRepository;

    public Connection startConnection(ConnectionRequest req, Long technicianId) {
        Connection c = new Connection();
        c.setSessionId(req.getSessionId());
        c.setStatus(com.techrepair.backend.enums.ConnectionStatus.ACTIVE);
        userRepository.findById(req.getClientId()).ifPresent(c::setClient);
        userRepository.findById(technicianId).ifPresent(c::setTechnician);
        return connectionRepository.save(c);
    }

    public Connection endConnection(Long connectionId) {
        Optional<Connection> opt = connectionRepository.findById(connectionId);
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
