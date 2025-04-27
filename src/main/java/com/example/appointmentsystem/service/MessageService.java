package com.example.appointmentsystem.service;

import com.example.appointmentsystem.dto.MessageDTO;
import com.example.appointmentsystem.dto.MessageRequestDTO;
import com.example.appointmentsystem.model.AppUser;
import com.example.appointmentsystem.model.Doctor;
import com.example.appointmentsystem.model.Message;
import com.example.appointmentsystem.model.NotificationType;
import com.example.appointmentsystem.repository.AppUserRepository;
import com.example.appointmentsystem.repository.DoctorRepository;
import com.example.appointmentsystem.repository.MessageRepository;
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
    private final AppUserRepository appUserRepository;
    private final NotificationService notificationService;

    public Message sendMessage(MessageRequestDTO dto) {
        String senderRole = dto.getSenderRole();
        if (senderRole == null || senderRole.isBlank()) {
            senderRole = appUserRepository.findById(dto.getSenderId())
                    .map(AppUser::getRole)
                    .orElse("UNKNOWN");
        }

        Message message = Message.builder()
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId()) 
                .senderRole(senderRole)
                .content(dto.getContent())
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        // 🛠️ Fix receiverId mapping depending on senderRole
        if ("PATIENT".equalsIgnoreCase(senderRole)) {
            doctorRepository.findById(dto.getReceiverId()).ifPresent(doctor -> {
                Long appUserId = doctor.getUser().getId();
                message.setReceiverId(appUserId);
            });
        } else if ("DOCTOR".equalsIgnoreCase(senderRole)) {
            appUserRepository.findById(dto.getReceiverId()).ifPresent(patient -> {
                message.setReceiverId(patient.getId());
            });
        }

        Message savedMessage = messageRepository.save(message);

        String senderName = getNameById(dto.getSenderId(), senderRole);

        notificationService.createNotification(
                message.getReceiverId(),
                "You received a new message from " + senderName + ".",
                NotificationType.MESSAGE
        );

        return savedMessage;
    }

    public Page<MessageDTO> getConversation(Long user1, Long user2, Pageable pageable) {
        Page<Message> messages = messageRepository.findConversation(user1, user2, pageable);

        return messages.map(message -> {
            String senderName = getNameById(message.getSenderId(), message.getSenderRole());
            String receiverRole = "PATIENT";
            if ("PATIENT".equalsIgnoreCase(message.getSenderRole())) {
                receiverRole = "DOCTOR";
            }
            String receiverName = getNameById(message.getReceiverId(), receiverRole);
        
            return new MessageDTO(
                message.getId(),
                message.getSenderId(),    // 👈 Added
                message.getReceiverId(),  // 👈 Added
                senderName,
                receiverName,
                message.getSenderRole(),
                message.getContent(),
                message.getTimestamp(),
                message.isRead(),
                getAvatarUrl(message.getSenderId(), message.getSenderRole()) // 👈 Added avatar URL
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
        if (role == null) {
            return "Unknown User";
        }
        if (role.equalsIgnoreCase("DOCTOR")) {
            return doctorRepository.findById(id)
                    .map(Doctor::getName)
                    .map(name -> "Dr. " + name)
                    .orElse("Unknown Doctor");
        } else if (role.equalsIgnoreCase("PATIENT")) {
            return appUserRepository.findById(id)
                    .map(AppUser::getFullName)
                    .orElse("Unknown Patient");
        }
        return "Unknown Role";
    }

    private String getAvatarUrl(Long id, String role) {
        if (role == null) {
            return null;
        }
    
        if (role.equalsIgnoreCase("DOCTOR")) {
            return doctorRepository.findById(id)
                    .map(Doctor::getUser)
                    .map(AppUser::getAvatarUrl)
                    .orElse(null);
        } else if (role.equalsIgnoreCase("PATIENT")) {
            return appUserRepository.findById(id)
                    .map(AppUser::getAvatarUrl)
                    .orElse(null);
        }
    
        return null;
    }
    

    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getSenderId().equals(userId) && !message.getReceiverId().equals(userId)) {
            throw new RuntimeException("You are not authorized to delete this message.");
        }

        messageRepository.deleteById(messageId);
    }

    public List<AppUser> findChatPartners(Long userId) {
        List<Long> partnerIds = messageRepository.findChatPartnerIds(userId);
        return appUserRepository.findAllById(partnerIds);
    }
    
}
