package com.joom.calendar.event;

import io.swagger.v3.oas.annotations.Parameter;
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
            @Parameter(example = "3") Long userId,
            @Parameter(example = "2022-12-26T09:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime from,
            @Parameter(example = "2022-12-30T18:00Z") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime to) {
        return eventService.findEventsByUserIdAndBounds(userId, from, to);
    }

    @GetMapping("/events/{id}")
    public EventDetailsDto findEventById(@PathVariable Long id) {
        return eventService.findById(id);
    }

    @PostMapping("/events")
    public EventDetailsDto createEvent(@RequestBody CreateEventCommand createEventCommand) {
        return eventService.createEvent(createEventCommand);
    }

    @PostMapping("/events/{eventId}/answer")
    public void saveAnswer(
            @Parameter(example = "3") @PathVariable Long eventId,
            @RequestBody SaveAnswerCommand saveAnswerCommand) {
        eventService.saveAnswer(eventId, saveAnswerCommand);
    }

}
