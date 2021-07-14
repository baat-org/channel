package org.baat.channel.repository;

import org.baat.channel.repository.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity, Long> {
    ChannelEntity findByName(String name);
}
