package com.example.notification_system.mapper;

import com.example.notification_system.dto.ChannelDTO;
import com.example.notification_system.entity.Channel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChannelMapper {

    @Autowired
    private ModelMapper modelMapper;

    // Convert Channel entity to ChannelDTO
    public ChannelDTO convertToDto(Channel channel) {
        return modelMapper.map(channel, ChannelDTO.class);
    }

    // Convert ChannelDTO to Channel entity
    public Channel convertToEntity(ChannelDTO channelDTO) {
        return modelMapper.map(channelDTO, Channel.class);
    }
}
