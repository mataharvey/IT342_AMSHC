package com.example.appointmentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDTO {
    private Long messageId;
    private Long senderId;     // ✅ Add this
    private Long receiverId;   // ✅ Add this
    private String senderName;
    private String receiverName;
    private String senderRole;
    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
    private String senderAvatarUrl; // (optional field)
}
