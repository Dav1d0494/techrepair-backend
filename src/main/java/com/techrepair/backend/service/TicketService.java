package com.techrepair.backend.service;

import com.techrepair.backend.dto.request.TicketRequest;
import com.techrepair.backend.dto.response.ApiResponse;
import com.techrepair.backend.model.Ticket;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.TicketRepository;
import com.techrepair.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public Ticket create(TicketRequest req, Long clientId) {
        Ticket t = new Ticket();
        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setPriority(req.getPriority());
        userRepository.findById(clientId).ifPresent(t::setClient);
        return ticketRepository.save(t);
    }

    public Page<Ticket> list(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }

    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }

    public Optional<Ticket> assignTechnician(Long ticketId, Long technicianId) {
        Optional<Ticket> opt = ticketRepository.findById(ticketId);
        if (opt.isEmpty()) return Optional.empty();
        Ticket t = opt.get();
        userRepository.findById(technicianId).ifPresent(t::setTechnician);
        t.setStatus(com.techrepair.backend.enums.TicketStatus.IN_PROGRESS);
        return Optional.of(ticketRepository.save(t));
    }

    public Optional<Ticket> closeTicket(Long ticketId) {
        Optional<Ticket> opt = ticketRepository.findById(ticketId);
        if (opt.isEmpty()) return Optional.empty();
        Ticket t = opt.get();
        t.setStatus(com.techrepair.backend.enums.TicketStatus.RESOLVED);
        t.setClosedAt(Instant.now());
        return Optional.of(ticketRepository.save(t));
    }
}
