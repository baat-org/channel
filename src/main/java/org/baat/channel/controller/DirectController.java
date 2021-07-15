package org.baat.channel.controller;

import org.baat.channel.service.DirectService;
import org.baat.core.transfer.channel.Direct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class DirectController {
    @Autowired
    DirectService directService;

    @CrossOrigin
    @RequestMapping(value = "/directs", method = POST)
    public boolean createDirect(@RequestBody @NotNull final Direct direct) {
        return directService.createDirect(direct);
    }

    @CrossOrigin
    @RequestMapping(value = "/directs", method = DELETE)
    public boolean removeDirect(@RequestBody @NotNull final Direct direct) {
        return directService.removeDirect(direct);
    }


    @CrossOrigin
    @RequestMapping(value = "/directs/{userId}", method = GET)
    public Set<Long> getDirectUserIds(@PathVariable("userId") @Positive @NotNull final Long userId) {
        return directService.getDirectUserIds(userId);
    }
}