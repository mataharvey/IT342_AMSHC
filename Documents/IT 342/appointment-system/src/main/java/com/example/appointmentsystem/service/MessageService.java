package com.example.appointmentsystem.service;

import com.example.appointmentsystem.model.Message;
import com.example.appointmentsystem.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(Message message) {
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1, Long user2) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(
                user1, user2, user1, user2);
    }
}
