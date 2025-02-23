package com.example.notification_system.controller;

import com.example.notification_system.dto.NotificationDTO;
import com.example.notification_system.entity.Notification;
import com.example.notification_system.mapper.NotificationMapper;
import com.example.notification_system.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationMapper notificationMapper;

    @PostMapping
    public NotificationDTO sendNotification(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationMapper.convertToEntity(notificationDTO);
        Notification sentNotification = notificationService.sendNotification(notification, notificationDTO.getChannelName());
        return notificationMapper.convertToDto(sentNotification);
    }

}
