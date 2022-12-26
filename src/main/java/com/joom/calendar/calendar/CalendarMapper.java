package com.joom.calendar.calendar;

import org.mapstruct.Mapper;

@Mapper
public interface CalendarMapper {
    Calendar map(CreateCalendarCommand createUserCommand);
}
