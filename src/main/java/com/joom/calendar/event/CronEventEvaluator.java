package com.joom.calendar.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CronEventEvaluator {

    private final EventMapper eventMapper;

    public List<EventDetailsDto> generateOnTheIntervalAndMap(Event event, LocalDateTime left, LocalDateTime right) {
        if (event.getCron() == null) {
            return List.of(eventMapper.map(event));
        } else {
            List<EventDetailsDto> resultList = new ArrayList<>();
            CronExpression cronExpression = CronExpression.parse(event.getCron());
            LocalDateTime eventStartDateTime = event.getStartDateTime();
            LocalDateTime eventEndDateTime = event.getEndDateTime();
            Duration eventDuration = Duration.between(eventStartDateTime, eventEndDateTime);
            while(eventStartDateTime.isBefore(right)) {
                if (!eventStartDateTime.isBefore(left)) {
                    eventEndDateTime = eventStartDateTime.plus(eventDuration);
                    resultList.add(eventMapper.map(event, eventStartDateTime, eventEndDateTime));
                }
                eventStartDateTime = cronExpression.next(eventStartDateTime);
            }
            return resultList;
        }
    }
}
