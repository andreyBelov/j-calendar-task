package com.joom.calendar.invitee;

import com.joom.calendar.event.Event;
import com.joom.calendar.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "INVITEES")
public class Invitee {

    @EmbeddedId
    private InviteeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "EVENT_ID")
    private Event event;

    @Enumerated(EnumType.STRING)
    private Answer answer;

    public Invitee(User user, Event event) {
        this.user = user;
        this.event = event;
        this.answer = Answer.UNKNOWN;
        this.id = new InviteeId(user.getId(), user.getId());
    }
}
