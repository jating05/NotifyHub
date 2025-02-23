package com.example.notification_system.service.impl;

import com.example.notification_system.dto.NotificationDTO;
import com.example.notification_system.entity.Channel;
import com.example.notification_system.entity.Notification;
import com.example.notification_system.enums.ChannelStatus;
import com.example.notification_system.enums.NotificationStatus;
import com.example.notification_system.enums.NotificationType;
import com.example.notification_system.repository.ChannelRepository;
import com.example.notification_system.repository.NotificationRepository;
import com.example.notification_system.service.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private TwilioSmsSender smsSender;

    @Autowired
    private SendGridEmailSender emailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChannelRepository channelRepository;

    private NotificationDTO notificationDTO;

    @Transactional
    @Override
    public Notification sendNotification(Notification notification, String channelName) {
        // Fetch the channel
        Optional<Channel> channelOpt = channelRepository.findByName(channelName);
        if (!channelOpt.isPresent()) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Channel not found");
        }

        Channel channel = channelOpt.get();

        // Check if the channel is enabled
        if (!ChannelStatus.ENABLED.equals(channel.getChannelStatus())) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Channel is not enabled");
        }

        boolean isExpired = isChannelExpired(channel);
        if (isExpired) {
            notification.setChannel(channel);
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Channel has expired");
        }

        // Set channel and other details
        notification.setChannel(channel);
        notification.setNotificationType(notification.getNotificationType());
        notification.setNotificationStatus(NotificationStatus.IN_PROGRESS);
        notification.setSubject(notification.getSubject());
        notificationRepository.save(notification);


        try {
            if (NotificationType.SMS.equals(notification.getNotificationType())) {
                smsSender.sendSms(notification.getRecipient(), notification.getMessage());
            } else if (NotificationType.EMAIL.equals(notification.getNotificationType())) {
                emailSender.sendEmail(notification.getRecipient(), notification.getSubject(), notification.getMessage());
            }

            // Update status to SUCCESS
            notification.setNotificationStatus(NotificationStatus.SUCCESS);
        } catch (Exception e) {
            // Update status to FAILED
            notification.setNotificationStatus(NotificationStatus.FAILED);
            notificationRepository.save(notification);
            throw new RuntimeException("Error Occurred", e);
        }
        notificationRepository.save(notification);
        return notification;
    }

    private boolean isChannelExpired(Channel channel) {
        // Assuming you have a 'createdAt' field in Channel
        LocalDateTime expirationMoment = channel.getCreatedAt().plusSeconds(channel.getExpiryTime());
        return LocalDateTime.now().isAfter(expirationMoment);
    }
}
