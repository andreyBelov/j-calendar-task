package com.joom.calendar.calendar;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/calendars")
    public Page<Calendar> findAllCalendars(@ParameterObject Pageable pageable) {
        return calendarService.findCalendars(pageable);
    }

    @PostMapping("/calendars")
    public CalendarDetailsDto createCalendar(@RequestBody CreateCalendarCommand createCalendarCommand) {
        return calendarService.saveCalendar(createCalendarCommand);
    }

}
