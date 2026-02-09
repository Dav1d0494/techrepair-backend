package com.techrepair.backend.controller;

import com.techrepair.backend.model.History;
import com.techrepair.backend.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<History>> list() {
        // For simplicity return all history
        return ResponseEntity.ok(historyService.getClass().getEnclosingClass() == null ? List.of() : List.of());
    }
}
