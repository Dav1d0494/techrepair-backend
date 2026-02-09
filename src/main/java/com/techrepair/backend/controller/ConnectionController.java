package com.techrepair.backend.controller;

import com.techrepair.backend.dto.request.ConnectionRequest;
import com.techrepair.backend.model.Connection;
import com.techrepair.backend.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/connections")
@Validated
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @PostMapping
    public ResponseEntity<Connection> start(@Valid @RequestBody ConnectionRequest req, @RequestParam Long technicianId) {
        return ResponseEntity.ok(connectionService.startConnection(req, technicianId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Connection>> active() {
        return ResponseEntity.ok(connectionService.getActiveConnections());
    }
}
