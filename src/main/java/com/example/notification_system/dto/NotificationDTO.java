package com.example.notification_system.dto;

import com.example.notification_system.enums.NotificationStatus;
import com.example.notification_system.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private String recipient;
    private String message;
    private NotificationType notificationType;
    //private NotificationStatus notificationStatus;
    private String channelName; // Added channelName
    private String subject;
}
