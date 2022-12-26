package com.joom.calendar.event;

import com.joom.calendar.calendar.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
