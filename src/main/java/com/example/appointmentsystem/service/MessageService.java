package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.MessageDTO;
import com.example.appointmentsystem.dto.MessageRequestDTO;
import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.model.Message;
import com.example.appointmentsystem.model.NotificationType;
import com.example.appointmentsystem.model.User;
import com.example.appointmentsystem.repository.DoctorRepository;
import com.example.appointmentsystem.repository.MessageRepository;
import com.example.appointmentsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;


    public Message sendMessage(MessageRequestDTO dto) {
        Message message = Message.builder()
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId())
                .senderRole(dto.getSenderRole())
                .content(dto.getContent())
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        Message savedMessage = messageRepository.save(message);

        // 🔔 Auto-create notification for the receiver
        String content = "You have a new message from " + dto.getSenderRole();
        notificationService.createNotification(
                dto.getReceiverId(),
                content,
                NotificationType.MESSAGE
        );

        return savedMessage;
    }


    public Page<MessageDTO> getConversation(Long user1, Long user2, Pageable pageable) {
        Page<Message> messages = messageRepository.findConversation(user1, user2, pageable);

        return messages.map(message -> {
            String senderName = getNameById(message.getSenderId(), message.getSenderRole());
            String receiverRole = message.getSenderRole().equalsIgnoreCase("PATIENT") ? "DOCTOR" : "PATIENT";
            String receiverName = getNameById(message.getReceiverId(), receiverRole);

            return new MessageDTO(
                    message.getId(),
                    senderName,
                    receiverName,
                    message.getSenderRole(),
                    message.getContent(),
                    message.getTimestamp(),
                    message.isRead()
            );
        });
    }

    public void markMessagesAsRead(Long receiverId, Long senderId) {
        List<Message> messages = messageRepository.findUnreadByReceiverAndSender(receiverId, senderId);
        for (Message message : messages) {
            message.setRead(true);
        }
        messageRepository.saveAll(messages);
    }

    private String getNameById(Long id, String role) {
        if (role.equalsIgnoreCase("DOCTOR")) {
            return doctorRepository.findById(id)
                    .map(Doctor::getName)
                    .map(name -> "Dr. " + name)
                    .orElse("Unknown Doctor");
        } else {
            return userRepository.findById(id)
                    .map(User::getFullName)
                    .orElse("Unknown Patient");
        }
    }
}
