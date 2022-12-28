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

    @GetMapping("/events")
    public Page<Event> findAllEvents(@ParameterObject Pageable pageable) {
        return eventService.findEvents(pageable);
    }

    @GetMapping("/events/{id}")
    public EventDetailsDto findEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PostMapping("/events")
    public void createEvent(@RequestBody CreateEventCommand createEventCommand) {
        eventService.createEvent(createEventCommand);
    }

    @PostMapping("/events/{id}/answer")
    public void saveAnswer(@PathVariable Long id, @RequestBody SaveAnswerCommand saveAnswerCommand) {
        eventService.saveAnswer(id, saveAnswerCommand);
    }

}
