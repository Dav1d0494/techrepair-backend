package com.techrepair.backend.service;

import com.techrepair.backend.model.Notification;
import com.techrepair.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public Notification send(Notification notification) {
        Notification saved = notificationRepository.save(notification);
        try {
            messagingTemplate.convertAndSendToUser(notification.getUser().getEmail(), "/topic/notifications", saved);
        } catch (Exception ignored) {}
        return saved;
    }

    public List<Notification> listForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
