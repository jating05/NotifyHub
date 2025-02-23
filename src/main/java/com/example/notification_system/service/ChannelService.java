package com.example.notification_system.service;

import com.example.notification_system.entity.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelService {
    List<Channel> getAllChannels();
    Optional<Channel> getChannelById(Integer id);
    Channel createChannel(Channel channel);
    Optional<Channel> updateChannel(Integer id, Channel channelDetails);
    boolean deleteChannel(Integer id);
}