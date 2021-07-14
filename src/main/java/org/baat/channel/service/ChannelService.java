package org.baat.channel.service;


import org.baat.channel.repository.ChannelRepository;
import org.baat.channel.repository.ChannelUserRepository;
import org.baat.channel.repository.entity.ChannelEntity;
import org.baat.channel.repository.entity.ChannelUserEntity;
import org.baat.core.transfer.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    ChannelUserRepository channelUserRepository;

    public Channel createChannel(@Valid @NotNull final Channel channel) {
        if (channel.getId() != null) {
            throw new IllegalArgumentException("Channel Id can't be passed when creating Channel");
        }

        if (channelRepository.findByName(channel.getName()) != null) {
            throw new IllegalArgumentException("Channel with this name already exists");
        }

        final ChannelEntity savedChannel = channelRepository.save(new ChannelEntity(channel.getName()));
        return new Channel(savedChannel.getId(), savedChannel.getName());
    }

    public boolean deleteChannel(@Positive final long channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        channelUserRepository.deleteByChannelId(channelId);
        channelRepository.deleteById(channelId);

        return true;
    }

    public boolean updateChannel(@Valid @NotNull final Channel channel) {
        if (channel.getId() == null) {
            throw new IllegalArgumentException("Channel Id must be passed when updating Channel");
        }

        if (!channelRepository.existsById(channel.getId())) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        channelRepository.save(new ChannelEntity(channel.getId(), channel.getName()));

        return true;
    }

    public boolean addUsersToChannel(@Positive final long channelId, @NotEmpty final Set<Long> userIds) {
        if (!channelRepository.existsById(channelId)) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        userIds.stream()
                .map(userId -> new ChannelUserEntity(channelId, userId))
                .filter(channelUserEntity -> !channelUserRepository.existsById(channelUserEntity))
                .forEach(channelUserEntity -> channelUserRepository.save(channelUserEntity));
        return true;
    }

    public boolean removeUserFromChannel(@Positive final long channelId, @Positive final long userId) {
        if (!channelRepository.existsById(channelId)) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        channelUserRepository.delete(new ChannelUserEntity(channelId, userId));
        return true;
    }

    public List<Channel> getChannelsForUser(@Positive final long userId) {
        final List<ChannelUserEntity> channelUserEntities = channelUserRepository.findByUserId(userId);
        return channelUserEntities.stream()
                .filter(channelUserEntity -> channelRepository.existsById(channelUserEntity.getChannelId()))
                .map(channelUserEntity -> channelRepository.getById(channelUserEntity.getChannelId()))
                .map(channelEntity -> new Channel(channelEntity.getId(), channelEntity.getName()))
                .sorted(Comparator.comparing(Channel::getName))
                .collect(Collectors.toList());
    }

    public Set<Long> getUserIdsInChannel(@Positive final long channelId) {
        if (!channelRepository.existsById(channelId)) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        final List<ChannelUserEntity> channelUserEntities = channelUserRepository.findByChannelId(channelId);
        return channelUserEntities.stream()
                .map(ChannelUserEntity::getUserId)
                .collect(Collectors.toSet());
    }
}
