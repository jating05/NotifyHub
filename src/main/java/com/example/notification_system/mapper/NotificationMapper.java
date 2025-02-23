package com.example.notification_system.mapper;

import com.example.notification_system.dto.ChannelDTO;
import com.example.notification_system.dto.NotificationDTO;
import com.example.notification_system.entity.Channel;
import com.example.notification_system.entity.Notification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    @Autowired
    private ModelMapper modelMapper;


    public NotificationDTO convertToDto(Notification notification) {
        //return modelMapper.map(notification, NotificationDTO.class);
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setRecipient(notification.getRecipient());
        dto.setMessage(notification.getMessage());
        dto.setNotificationType(notification.getNotificationType());
        // dto.setNotificationStatus(notification.getNotificationStatus()); // Optional
        dto.setChannelName(notification.getChannel().getName());
        return dto;
    }

    public Notification convertToEntity(NotificationDTO notificationDTO) {
        //return modelMapper.map(notificationDTO, Notification.class);
        Notification notification = new Notification();
        notification.setRecipient(notificationDTO.getRecipient());
        notification.setMessage(notificationDTO.getMessage());
        notification.setNotificationType(notificationDTO.getNotificationType());
        notification.setSubject(notificationDTO.getSubject());
        // Do not set channel here; it's handled in the service layer
        return notification;
    }
}
