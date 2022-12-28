package com.joom.calendar.invitee;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class InviteeId implements Serializable {

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "EVENT_ID")
    private Long eventId;

    public InviteeId(
            Long userId,
            Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public InviteeId() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        InviteeId that = (InviteeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, eventId);
    }
}
