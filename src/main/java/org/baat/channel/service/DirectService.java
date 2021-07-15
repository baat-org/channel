package org.baat.channel.service;


import org.baat.channel.repository.DirectRepository;
import org.baat.channel.repository.entity.DirectEntity;
import org.baat.core.transfer.channel.Direct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class DirectService {

    @Autowired
    DirectRepository directRepository;

    public boolean createDirect(@Valid Direct direct) {
        if (directRepository.existsById(new DirectEntity(direct.getFirstUserId(), direct.getSecondUserId()))) {
            return true;
        }

        if (directRepository.existsById(new DirectEntity(direct.getSecondUserId(), direct.getFirstUserId()))) {
            return true;
        }

        directRepository.save(new DirectEntity(direct.getFirstUserId(), direct.getSecondUserId()));
        return true;
    }

    public boolean removeDirect(@Valid Direct direct) {
        directRepository.delete(new DirectEntity(direct.getFirstUserId(), direct.getSecondUserId()));
        directRepository.delete(new DirectEntity(direct.getSecondUserId(), direct.getFirstUserId()));
        return true;
    }

    public Set<Long> getDirectUserIds(@Positive final long userId) {
        return Stream
                .concat(directRepository.findByFirstUserId(userId).stream().map(DirectEntity::getSecondUserId),
                        directRepository.findBySecondUserId(userId).stream().map(DirectEntity::getFirstUserId))
                .collect(Collectors.toSet());
    }
}
