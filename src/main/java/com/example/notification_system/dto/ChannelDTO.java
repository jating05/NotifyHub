package com.example.notification_system.dto;

import com.example.notification_system.enums.ChannelStatus;
import com.example.notification_system.enums.NotificationStatus;
import com.example.notification_system.enums.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChannelDTO {

    private Integer id;
    private String name;
    private Integer expiryTime;
    private ChannelStatus channelStatus;
    private List<String> notificationType;
}
