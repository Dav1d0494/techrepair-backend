package com.techrepair.backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RemoteConnectionHandler {

    @MessageMapping("/remote.connect")
    @SendTo("/topic/remote")
    public String connect(String payload) {
        return payload;
    }
}
