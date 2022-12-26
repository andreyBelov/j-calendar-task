package com.joom.calendar.calendar;

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

    @GetMapping("/users/{userId}/calendars")
    public Page<Calendar> findAllCalendars(@PathVariable Long userId, @ParameterObject Pageable pageable) {
        return calendarService.findCalendars(pageable);
    }

    @PostMapping("/users/{userId}/calendars")
    public void createCalendar(@PathVariable Long userId, @RequestBody CreateCalendarCommand createCalendarCommand) {
        calendarService.saveCalendar(userId, createCalendarCommand);
    }

}
