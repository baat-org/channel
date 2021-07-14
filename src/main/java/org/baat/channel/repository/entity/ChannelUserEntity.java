package org.baat.channel.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "channel_user")
@IdClass(ChannelUserEntity.class)
public class ChannelUserEntity implements Serializable {

    @Id
    private Long channelId;

    @Id
    private Long userId;

    public ChannelUserEntity() {
    }

    public ChannelUserEntity(final Long channelId, final Long userId) {
        this.channelId = channelId;
        this.userId = userId;
    }

    @Column(name = "channel_id")
    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelUserEntity that = (ChannelUserEntity) o;
        return Objects.equals(channelId, that.channelId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, userId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChannelUserEntity{");
        sb.append("channelId=").append(channelId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
