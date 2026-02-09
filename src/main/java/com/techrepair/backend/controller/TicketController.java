package com.techrepair.backend.controller;

import com.techrepair.backend.dto.request.TicketRequest;
import com.techrepair.backend.dto.response.ApiResponse;
import com.techrepair.backend.dto.response.TicketResponse;
import com.techrepair.backend.model.Ticket;
import com.techrepair.backend.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    public ResponseEntity<Ticket> create(@Valid @RequestBody TicketRequest req, @RequestParam Long clientId) {
        return ResponseEntity.ok(ticketService.create(req, clientId));
    }

    @GetMapping
    public ResponseEntity<Page<Ticket>> list(Pageable pageable) {
        return ResponseEntity.ok(ticketService.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Ticket> opt = ticketService.findById(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<?> assign(@PathVariable Long id, @RequestParam Long technicianId) {
        Optional<Ticket> opt = ticketService.assignTechnician(id, technicianId);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> close(@PathVariable Long id) {
        Optional<Ticket> opt = ticketService.closeTicket(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
