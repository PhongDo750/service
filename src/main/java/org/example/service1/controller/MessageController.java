package org.example.service1.controller;

import jakarta.jms.JMSException;
import lombok.AllArgsConstructor;
import org.example.service1.service.MessageSenderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin()
@RequestMapping("/api/v1/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageSenderService messageSenderService;

    @PostMapping()
    public void sendMessage() throws JMSException {
        messageSenderService.sendMessage();
    }
}
