package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.model.Message;
import com.example.appointmentsystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(
            @RequestParam Long user1,
            @RequestParam Long user2) {
        return messageService.getConversation(user1, user2);
    }
}
