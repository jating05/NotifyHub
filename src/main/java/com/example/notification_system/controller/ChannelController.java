package com.example.notification_system.controller;

import com.example.notification_system.dto.ChannelDTO;
import com.example.notification_system.entity.Channel;
import com.example.notification_system.mapper.ChannelMapper;
import com.example.notification_system.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/channels")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private ChannelMapper channelMapper;

    // Get all channels
    @GetMapping
    public List<ChannelDTO> getAllChannels() {
        List<Channel> channels = channelService.getAllChannels();
        return channels.stream().map(channelMapper::convertToDto).collect(Collectors.toList());
    }

    // Get a specific channel by id
    @GetMapping("/{id}")
    public ResponseEntity<ChannelDTO> getChannelById(@PathVariable Integer id) {
        Optional<Channel> channel = channelService.getChannelById(id);
        if (channel.isPresent()) {
            return ResponseEntity.ok(channelMapper.convertToDto(channel.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new channel
    @PostMapping
    public ChannelDTO createChannel(@RequestBody ChannelDTO channelDTO) {
        Channel channel = channelMapper.convertToEntity(channelDTO);
        Channel createdChannel = channelService.createChannel(channel);
        return channelMapper.convertToDto(createdChannel);
    }

    // Update an existing channel by id
    @PutMapping("/{id}")
    public ResponseEntity<ChannelDTO> updateChannel(@PathVariable Integer id, @RequestBody ChannelDTO channelDetails) {
        Channel channelEntity = channelMapper.convertToEntity(channelDetails);
        Optional<Channel> updatedChannel = channelService.updateChannel(id, channelEntity);
        if (updatedChannel.isPresent()) {
            return ResponseEntity.ok(channelMapper.convertToDto(updatedChannel.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a channel by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Integer id) {
        boolean isDeleted = channelService.deleteChannel(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}