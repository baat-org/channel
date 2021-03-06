package org.baat.channel.controller;

import org.baat.channel.service.ChannelService;
import org.baat.core.transfer.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @CrossOrigin
    @RequestMapping(value = "/channels", method = POST)
    public Channel createChannel(@RequestBody @NotNull final Channel channel) {
        return channelService.createChannel(channel);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels", method = PUT)
    public boolean updateChannel(@RequestBody @NotNull final Channel channel) {
        return channelService.updateChannel(channel);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels/{channelId}/archive", method = PUT)
    public boolean archiveChannel(@PathVariable("channelId") @NotNull @Positive final Long channelId) {
        return channelService.archiveChannel(channelId);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels/{channelId}/users", method = PUT)
    public boolean addUsersToChannel(@PathVariable("channelId") @NotNull @Positive final Long channelId, @RequestBody @NotEmpty final Set<Long> userIds) {
        return channelService.addUsersToChannel(channelId, userIds);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels/{channelId}/users/{userId}", method = DELETE)
    public boolean removeUserFromChannel(@PathVariable("channelId") @NotNull @Positive final Long channelId, @PathVariable("userId") @NotNull @Positive final Long userId) {
        return channelService.removeUserFromChannel(channelId, userId);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels", method = GET)
    public List<Channel> getAllChannels() {
        return channelService.getAllChannels();
    }

    @CrossOrigin
    @RequestMapping(value = "/users/{userId}/channels", method = GET)
    public List<Channel> getChannelsForUser(@PathVariable("userId") @NotNull @Positive final Long userId) {
        return channelService.getChannelsForUser(userId);
    }

    @CrossOrigin
    @RequestMapping(value = "/channels/{channelId}/users", method = GET)
    public Set<Long> getUserIdsInChannel(@PathVariable("channelId") @NotNull @Positive final Long channelId) {
        return channelService.getUserIdsInChannel(channelId);
    }
}