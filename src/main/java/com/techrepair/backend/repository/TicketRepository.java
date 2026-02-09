package com.techrepair.backend.repository;

import com.techrepair.backend.enums.TicketPriority;
import com.techrepair.backend.enums.TicketStatus;
import com.techrepair.backend.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);
    long countByPriority(TicketPriority priority);
    Page<Ticket> findByClientId(Long clientId, Pageable pageable);
}
