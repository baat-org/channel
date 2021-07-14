package org.baat.channel.repository;

import org.baat.channel.repository.entity.ChannelUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelUserRepository extends JpaRepository<ChannelUserEntity, ChannelUserEntity> {
    List<ChannelUserEntity> findByUserId(long userId);

    List<ChannelUserEntity> findByChannelId(long channelId);

    List<ChannelUserEntity> deleteByChannelId(long channelId);
}
