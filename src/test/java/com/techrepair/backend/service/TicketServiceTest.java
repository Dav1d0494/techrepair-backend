package com.techrepair.backend.service;

import com.techrepair.backend.model.Ticket;
import com.techrepair.backend.model.User;
import com.techrepair.backend.repository.TicketRepository;
import com.techrepair.backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketService ticketService;

    public TicketServiceTest() { MockitoAnnotations.openMocks(this); }

    @Test
    public void assignTechnician_assigns_when_exists() {
        Ticket t = new Ticket();
        t.setId(1L);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(t));
        User tech = new User(); tech.setId(2L);
        when(userRepository.findById(2L)).thenReturn(Optional.of(tech));
        when(ticketRepository.save(t)).thenReturn(t);

        Optional<Ticket> res = ticketService.assignTechnician(1L, 2L);
        assertThat(res).isPresent();
        assertThat(res.get().getTechnician()).isNotNull();
    }
}
