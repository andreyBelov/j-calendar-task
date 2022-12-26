package com.joom.calendar.event;

import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/calendars/{calendarId}/events")
    public Page<Event> findAllEvents(@ParameterObject Pageable pageable) {
        return eventService.findEvents(pageable);
    }

    @PostMapping("/calendars/{calendarId}/events")
    public void createEvent(@PathVariable Long calendarId, @RequestBody CreateEventCommand createEventCommand) {
        System.out.println(createEventCommand.getStartDateTime());
        eventService.createEvent(calendarId, createEventCommand);
    }

}
