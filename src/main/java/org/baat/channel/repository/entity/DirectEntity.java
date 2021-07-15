package org.baat.channel.repository.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "direct")
@IdClass(DirectEntity.class)
public class DirectEntity implements Serializable {

    @Id
    private Long firstUserId;

    @Id
    private Long secondUserId;

    public DirectEntity() {
    }

    public DirectEntity(final Long firstUserId, final Long secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;
    }

    @Column(name = "first_user_id")
    public Long getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(Long firstUserId) {
        this.firstUserId = firstUserId;
    }

    @Column(name = "second_user_id")
    public Long getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(Long secondUserId) {
        this.secondUserId = secondUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectEntity that = (DirectEntity) o;
        return Objects.equals(firstUserId, that.firstUserId) && Objects.equals(secondUserId, that.secondUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstUserId, secondUserId);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DirectEntity{");
        sb.append("firstUserId=").append(firstUserId);
        sb.append(", secondUserId=").append(secondUserId);
        sb.append('}');
        return sb.toString();
    }
}
