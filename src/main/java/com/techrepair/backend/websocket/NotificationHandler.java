package com.techrepair.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationHandler {

    @MessageMapping("/notify")
    @SendToUser("/topic/notifications")
    public String notifyUser(String payload) {
        return payload;
    }
}
