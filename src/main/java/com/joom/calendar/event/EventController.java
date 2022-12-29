package com.joom.calendar.event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventDetailsDto> findAllEvents(
            Long userId,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime from,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime to) {
        return eventService.findEventsByUserIdAndBounds(userId, from, to);
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
