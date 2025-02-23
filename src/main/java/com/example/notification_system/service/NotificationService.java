package com.example.notification_system.service;

import com.example.notification_system.entity.Notification;

public interface NotificationService {
    Notification sendNotification(Notification notification, String channel);
}

