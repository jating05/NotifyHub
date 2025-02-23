package com.example.notification_system.service.impl;

import com.example.notification_system.entity.Channel;
import com.example.notification_system.repository.ChannelRepository;
import com.example.notification_system.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public List<Channel> getAllChannels() {
        return channelRepository.findAll();
    }

    @Override
    public Optional<Channel> getChannelById(Integer id) {
        return channelRepository.findById(id);
    }
    @Override
    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Optional<Channel> updateChannel(Integer id, Channel channelDetails) {
        Optional<Channel> channelOptional = channelRepository.findById(id);

        if (channelOptional.isPresent()) {
            Channel channel = channelOptional.get();

            // Update fields only if they are not null in the provided channelDetails
            if (channelDetails.getName() != null) {
                channel.setName(channelDetails.getName());
            }
            if (channelDetails.getExpiryTime() != null) {
                channel.setExpiryTime(channelDetails.getExpiryTime());
            }
            if (channelDetails.getChannelStatus() != null) {
                channel.setChannelStatus(channelDetails.getChannelStatus());
            }
            if (channelDetails.getNotificationType() != null) {
                channel.setNotificationType(channelDetails.getNotificationType());
            }
            return Optional.of(channelRepository.save(channel));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteChannel(Integer id) {
        Optional<Channel> channelOptional = channelRepository.findById(id);
        if (channelOptional.isPresent()) {
            channelRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}