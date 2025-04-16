package com.example.appointmentsystem.controller;

import com.example.appointmentsystem.dto.MessageRequest;
import com.example.appointmentsystem.model.Message;
import com.example.appointmentsystem.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/send")
    public Message send(@RequestBody MessageRequest request) {
        Message message = Message.builder()
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .content(request.getContent())
                .build();
        return messageService.sendMessage(message);
    }

    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return messageService.getConversation(senderId, receiverId);
    }
}
