package com.techrepair.backend.service;

import com.techrepair.backend.dto.request.OfflineOperationType;
import com.techrepair.backend.dto.request.OfflineTicketOperationRequest;
import com.techrepair.backend.dto.request.TicketRequest;
import com.techrepair.backend.dto.response.OfflineOperationResult;
import com.techrepair.backend.model.Ticket;
import com.techrepair.backend.repository.TicketRepository;
import com.techrepair.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    public List<Ticket> getChangesSince(Instant since, int limit) {
        Pageable pageable = PageRequest.of(0, Math.max(1, Math.min(limit, 500)));
        if (since == null) {
            return ticketRepository.findAllByOrderByUpdatedAtAsc(pageable).getContent();
        }
        return ticketRepository.findByUpdatedAtAfterOrderByUpdatedAtAsc(since, pageable).getContent();
    }

    public Instant getLastSyncToken() {
        return ticketRepository
                .findTopByOrderByUpdatedAtDesc()
                .map(Ticket::getUpdatedAt)
                .orElse(Instant.now());
    }

    public List<OfflineOperationResult> processOfflineOperations(List<OfflineTicketOperationRequest> operations) {
        List<OfflineOperationResult> results = new ArrayList<>();
        for (OfflineTicketOperationRequest operation : operations) {
            results.add(processOperation(operation));
        }
        return results;
    }

    private OfflineOperationResult processOperation(OfflineTicketOperationRequest operation) {
        String operationId = operation != null ? operation.getOperationId() : null;
        if (operation == null || operation.getType() == null) {
            return new OfflineOperationResult(operationId, false, "Operation type is required", null);
        }

        OfflineOperationType type = operation.getType();
        try {
            if (type == OfflineOperationType.CREATE) {
                if (operation.getTicket() == null || operation.getClientId() == null) {
                    return new OfflineOperationResult(operationId, false, "ticket and clientId are required", null);
                }
                Ticket created = create(operation.getTicket(), operation.getClientId());
                return new OfflineOperationResult(operationId, true, "Created", created);
            }

            if (type == OfflineOperationType.ASSIGN_TECHNICIAN) {
                if (operation.getTicketId() == null || operation.getTechnicianId() == null) {
                    return new OfflineOperationResult(operationId, false, "ticketId and technicianId are required", null);
                }
                return assignTechnician(operation.getTicketId(), operation.getTechnicianId())
                        .map(ticket -> new OfflineOperationResult(operationId, true, "Assigned", ticket))
                        .orElseGet(() -> new OfflineOperationResult(operationId, false, "Ticket not found", null));
            }

            if (type == OfflineOperationType.CLOSE) {
                if (operation.getTicketId() == null) {
                    return new OfflineOperationResult(operationId, false, "ticketId is required", null);
                }
                return closeTicket(operation.getTicketId())
                        .map(ticket -> new OfflineOperationResult(operationId, true, "Closed", ticket))
                        .orElseGet(() -> new OfflineOperationResult(operationId, false, "Ticket not found", null));
            }

            return new OfflineOperationResult(operationId, false, "Unsupported operation", null);
        } catch (Exception ex) {
            return new OfflineOperationResult(operationId, false, "Operation failed: " + ex.getMessage(), null);
        }
    }
}
