package com.joom.calendar.event;

import com.joom.calendar.invitee.InviteeDetailsDto;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class EventDetailsDto {
    Long id;
    String name;
    String description;
    String place;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    List<InviteeDetailsDto> invitees;
}
