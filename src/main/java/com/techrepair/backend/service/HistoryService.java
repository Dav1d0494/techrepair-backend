package com.techrepair.backend.service;

import com.techrepair.backend.model.History;
import com.techrepair.backend.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public History record(History h) {
        return historyRepository.save(h);
    }
}
