package com.joom.calendar.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(imports = { CronExpressionGenerator.class })
public interface EventMapper {

    @Mappings(
            @Mapping(target = "cron", expression = "java(CronExpressionGenerator.mapToCronString(createEventCommand.getPredefinedSchedule(), createEventCommand.getStartDateTime()))")
    )
    Event map(CreateEventCommand createEventCommand);
}
