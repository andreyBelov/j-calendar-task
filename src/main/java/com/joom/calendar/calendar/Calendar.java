package com.joom.calendar.calendar;

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
@Table(name = "CALENDARS")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="OWNER_ID", nullable=false)
    private User user;

}
