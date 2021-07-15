package org.baat.channel.repository;

import org.baat.channel.repository.entity.DirectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectRepository extends JpaRepository<DirectEntity, DirectEntity> {

    List<DirectEntity> findByFirstUserId(long firstUserId);

    List<DirectEntity> findBySecondUserId(long secondUserId);
}
