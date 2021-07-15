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
import java.util.Optional;
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
        if (channel.isArchived()) {
            throw new IllegalArgumentException("Channel can't be created as archived");
        }

        if (channelRepository.findByName(channel.getName()) != null) {
            throw new IllegalArgumentException("Channel with this name already exists");
        }

        final ChannelEntity savedChannel = channelRepository.save(new ChannelEntity(null, channel.getName(), channel.getDescription(), channel.isArchived()));
        return new Channel(savedChannel.getId(), savedChannel.getName(), savedChannel.getDescription(), savedChannel.isArchived());
    }

    public boolean archiveChannel(@Positive final long channelId) {
        final Optional<ChannelEntity> foundChannelOptional = channelRepository.findById(channelId);
        if (foundChannelOptional.isEmpty()) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        final ChannelEntity channelToUpdate = foundChannelOptional.get();
        channelToUpdate.setArchived(true);
        channelRepository.save(channelToUpdate);
        return true;
    }

    public boolean updateChannel(@Valid @NotNull final Channel channel) {
        if (channel.getId() == null) {
            throw new IllegalArgumentException("Channel Id must be passed when updating Channel");
        }

        final Optional<ChannelEntity> foundChannelOptional = channelRepository.findById(channel.getId());
        if (foundChannelOptional.isEmpty()) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }

        final ChannelEntity channelToUpdate = foundChannelOptional.get();
        channelToUpdate.setName(channel.getName());
        channelToUpdate.setDescription(channel.getDescription());
        channelRepository.save(channelToUpdate);

        return true;
    }

    public boolean addUsersToChannel(@Positive final long channelId, @NotEmpty final Set<Long> userIds) {
        final Optional<ChannelEntity> foundChannelOptional = channelRepository.findById(channelId);
        if (foundChannelOptional.isEmpty()) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }
        if (foundChannelOptional.get().isArchived()) {
            throw new IllegalArgumentException("Users can't be added to an archived channel");
        }

        userIds.stream()
                .map(userId -> new ChannelUserEntity(channelId, userId))
                .filter(channelUserEntity -> !channelUserRepository.existsById(channelUserEntity))
                .forEach(channelUserEntity -> channelUserRepository.save(channelUserEntity));
        return true;
    }

    public boolean removeUserFromChannel(@Positive final long channelId, @Positive final long userId) {
        final Optional<ChannelEntity> foundChannelOptional = channelRepository.findById(channelId);
        if (foundChannelOptional.isEmpty()) {
            throw new IllegalArgumentException("Channel with passed id don't exist");
        }
        if (foundChannelOptional.get().isArchived()) {
            throw new IllegalArgumentException("Users can't be removed from an archived channel");
        }

        channelUserRepository.delete(new ChannelUserEntity(channelId, userId));
        return true;
    }

    public List<Channel> getAllChannels() {
        return channelRepository.findAll().stream()
                .map(channelEntity -> new Channel(channelEntity.getId(), channelEntity.getName(), channelEntity.getDescription(), channelEntity.isArchived()))
                .sorted(Comparator.comparing(Channel::getName))
                .collect(Collectors.toList());
    }

    public List<Channel> getChannelsForUser(@Positive final long userId) {
        final List<ChannelUserEntity> channelUserEntities = channelUserRepository.findByUserId(userId);
        return channelUserEntities.stream()
                .filter(channelUserEntity -> channelRepository.existsById(channelUserEntity.getChannelId()))
                .map(channelUserEntity -> channelRepository.getById(channelUserEntity.getChannelId()))
                .map(channelEntity -> new Channel(channelEntity.getId(), channelEntity.getName(), channelEntity.getDescription(), channelEntity.isArchived()))
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
