package com.techrepair.backend.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatHandler {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/chat")
    public String send(String message) {
        return message;
    }
}
