package com.joom.calendar.event;

import com.joom.calendar.calendar.Calendar;
import com.joom.calendar.invitee.Invitee;
import com.joom.calendar.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CALENDAR_ID", nullable=false)
    private Calendar calendar;
    private String description;
    private String place;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String cron;
    @Enumerated(EnumType.STRING)
    private EventVisibility eventVisibility;
    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Invitee> invitees = new ArrayList<>();

    public void addInvitees(List<User> users) {
        List<Invitee> invitees = users.stream()
                .map(u -> new Invitee(u, this))
                .toList();
        this.invitees.addAll(invitees);
    }

    public void removeUser(User user) {
        for (Iterator<Invitee> iterator = invitees.iterator();
             iterator.hasNext(); ) {
            Invitee invitee = iterator.next();
            if (invitee.getEvent().equals(this) &&
                    invitee.getUser().equals(user)) {
                iterator.remove();
                invitee.setEvent(null);
                invitee.setUser(null);
            }
        }
    }
}
