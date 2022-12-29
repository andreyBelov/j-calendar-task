package com.joom.calendar.planning;

import com.joom.calendar.event.CronEventEvaluator;
import com.joom.calendar.event.Event;
import com.joom.calendar.event.EventRepository;
import com.joom.calendar.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.threeten.extra.Interval;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.joom.calendar.event.EventSearchSpecs.byBounds;
import static com.joom.calendar.event.EventSearchSpecs.byUserIdIn;

@Service
@RequiredArgsConstructor
public class PlanningHelperService {

    private final EventRepository eventRepository;
    private final CronEventEvaluator cronEventEvaluator;

    public Interval findFirstFreeTimeslot(List<Long> userIds, int eventDurationInMinutes, LocalDateTime left, LocalDateTime right) {
        Specification<Event> byUserIdInAndBounds = byUserIdIn(userIds)
                .and(byBounds(left, right));
        List<Interval> busyTimeslots = eventRepository.findAll(byUserIdInAndBounds, Sort.by(Sort.Order.by("startDateTime"))).stream()
                .flatMap(e -> cronEventEvaluator.generateOnTheIntervalAndMap(e, left, right).stream())
                .map(dto -> Interval.of(dto.getStartDateTime().toInstant(ZoneOffset.UTC), dto.getEndDateTime().toInstant(ZoneOffset.UTC)))
                .toList();
        Optional<Interval> firstFreeTimeslotOpt = DateTimeUtils.findFirstFreeTimeslot(
                Interval.of(left.toInstant(ZoneOffset.UTC), right.toInstant(ZoneOffset.UTC)),
                busyTimeslots,
                Duration.of(eventDurationInMinutes, ChronoUnit.MINUTES)
        );
        return firstFreeTimeslotOpt.orElse(null);
    }
}
