package com.joom.calendar.util;

import lombok.experimental.UtilityClass;
import org.threeten.extra.Interval;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class DateTimeUtils {

    public Optional<Interval> findFirstFreeTimeslot(
            Interval bounds,
            List<Interval> busyTimeSlots,
            Duration eventDuration
    ) {
        var mergedBusyTimeslots = unionConnectedTimeslots(busyTimeSlots);
        return calculateFreeTimeslots(mergedBusyTimeslots, bounds).stream()
                .filter(interval -> interval.toDuration().compareTo(eventDuration) >= 0)
                .findFirst();
    }

    private List<Interval> calculateFreeTimeslots(List<Interval> busyTimeSlots, Interval bounds) {
        if (busyTimeSlots.isEmpty()) {
            return List.of(bounds);
        }
        List<Interval> freeTimeslots = new ArrayList<>();
        Instant freeTimeslotStart;
        Instant freeTimeslotEnd;
        Interval firstBusyTimeslot = busyTimeSlots.get(0);
        if (bounds.getStart().isBefore(firstBusyTimeslot.getStart())) {
            freeTimeslotStart = bounds.getStart();
            freeTimeslotEnd = firstBusyTimeslot.getStart();
            freeTimeslots.add(Interval.of(freeTimeslotStart, freeTimeslotEnd));
        }
        freeTimeslotStart = firstBusyTimeslot.getEnd();

        for (int i = 1; i < busyTimeSlots.size(); i++) {
            freeTimeslotEnd = busyTimeSlots.get(i).getStart();
            freeTimeslots.add(Interval.of(freeTimeslotStart, freeTimeslotEnd));
            freeTimeslotStart = busyTimeSlots.get(i).getEnd();
        }

        if (freeTimeslotStart.isBefore(bounds.getEnd())) {
            freeTimeslots.add(Interval.of(freeTimeslotStart, bounds.getEnd()));
        }

        return freeTimeslots;
    }

    public List<Interval> unionConnectedTimeslots(List<Interval> timeslots) {
        if (timeslots.isEmpty()) {
            return Collections.emptyList();
        }
        if (timeslots.size() == 1) {
            return timeslots;
        }
        List<Interval> result = new ArrayList<>();
        result.add(timeslots.get(0));
        for (var i = 1; i < timeslots.size(); i++) {
            int lastIndexInResultList = result.size() - 1;
            Interval lastInResultList = result.get(lastIndexInResultList);
            Interval current = timeslots.get(i);
            if (current.isConnected(lastInResultList)) {
                result.set(lastIndexInResultList, lastInResultList.union(current));
            } else {
                result.add(current);
            }
        }
        return result;
    }
}
