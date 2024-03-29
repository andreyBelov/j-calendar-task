package com.joom.calendar.event;

import com.joom.calendar.invitee.Invitee;
import com.joom.calendar.invitee.InviteeDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;

@Mapper(imports = { CronExpressionGenerator.class })
public interface EventMapper {

    @Mappings(
            @Mapping(target = "cron", expression = "java(CronExpressionGenerator.mapToCronString(createEventCommand.getPredefinedSchedule(), createEventCommand.getStartDateTime()))")
    )
    Event map(CreateEventCommand createEventCommand);

    EventDetailsDto map(Event e);

    @Mapping(target = "startDateTime", source = "startDateTime")
    @Mapping(target = "endDateTime", source = "endDateTime")
    EventDetailsDto map(Event e, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Mappings({
            @Mapping(target = "name", source = "invitee.user.name"),
            @Mapping(target = "answer", source = "invitee.answer")
    })
    InviteeDetailsDto map(Invitee invitee);
}
