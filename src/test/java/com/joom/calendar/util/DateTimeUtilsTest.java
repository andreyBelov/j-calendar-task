package com.joom.calendar.util;

import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAndIs;
import static org.hamcrest.MatcherAssert.assertThat;

public class DateTimeUtilsTest {

    @Test
    void findFirstFreeTimeslot() {
        Instant meeting1OfUser1Start = LocalDateTime.of(2023, 1, 1, 9, 0).toInstant(ZoneOffset.UTC);
        Instant meeting1OfUser1End = LocalDateTime.of(2023, 1, 1, 12, 0).toInstant(ZoneOffset.UTC);
        Instant meeting2OfUser1Start = LocalDateTime.of(2023, 1, 1, 10, 0).toInstant(ZoneOffset.UTC);
        Instant meeting2OfUser1End = LocalDateTime.of(2023, 1, 1, 14, 0).toInstant(ZoneOffset.UTC);

        Instant meeting1OfUser2Start = LocalDateTime.of(2023, 1, 1, 16, 0).toInstant(ZoneOffset.UTC);
        Instant meeting1OfUser2End = LocalDateTime.of(2023, 1, 1, 18, 0).toInstant(ZoneOffset.UTC);
        List<Interval> usersBusyTimeslots = List.of(
                Interval.of(meeting1OfUser1Start, meeting1OfUser1End),
                Interval.of(meeting2OfUser1Start, meeting2OfUser1End),
                Interval.of(meeting1OfUser2Start, meeting1OfUser2End)
        );

        Instant boundStart = LocalDateTime.of(2023, 1, 1, 9, 0).toInstant(ZoneOffset.UTC);
        Instant boundEnd = LocalDateTime.of(2023, 1, 1, 18, 0).toInstant(ZoneOffset.UTC);
        Interval bounds = Interval.of(boundStart, boundEnd);

        Duration eventDuration = Duration.of(2, ChronoUnit.HOURS);

        Optional<Interval> firstFreeTimeslot = DateTimeUtils.findFirstFreeTimeslot(bounds, usersBusyTimeslots, eventDuration);

        Instant expectedStart = LocalDateTime.of(2023, 1, 1, 14, 0).toInstant(ZoneOffset.UTC);
        Instant expectedEnd = LocalDateTime.of(2023, 1, 1, 16, 0).toInstant(ZoneOffset.UTC);
        Interval expectedInterval = Interval.of(expectedStart, expectedEnd);
        assertThat(firstFreeTimeslot, isPresentAndIs(expectedInterval));
    }

    @Test
    void findFirstFreeTimeslotNotFound() {
        Instant meeting1OfUser1Start = LocalDateTime.of(2023, 1, 1, 9, 0).toInstant(ZoneOffset.UTC);
        Instant meeting1OfUser1End = LocalDateTime.of(2023, 1, 1, 12, 0).toInstant(ZoneOffset.UTC);
        Instant meeting2OfUser1Start = LocalDateTime.of(2023, 1, 1, 10, 0).toInstant(ZoneOffset.UTC);
        Instant meeting2OfUser1End = LocalDateTime.of(2023, 1, 1, 14, 0).toInstant(ZoneOffset.UTC);

        Instant meeting1OfUser2Start = LocalDateTime.of(2023, 1, 1, 16, 0).toInstant(ZoneOffset.UTC);
        Instant meeting1OfUser2End = LocalDateTime.of(2023, 1, 1, 18, 0).toInstant(ZoneOffset.UTC);
        List<Interval> usersBusyTimeslots = List.of(
                Interval.of(meeting1OfUser1Start, meeting1OfUser1End),
                Interval.of(meeting2OfUser1Start, meeting2OfUser1End),
                Interval.of(meeting1OfUser2Start, meeting1OfUser2End)
        );

        Instant boundStart = LocalDateTime.of(2023, 1, 1, 9, 0).toInstant(ZoneOffset.UTC);
        Instant boundEnd = LocalDateTime.of(2023, 1, 1, 18, 0).toInstant(ZoneOffset.UTC);
        Interval bounds = Interval.of(boundStart, boundEnd);

        Duration eventDuration = Duration.of(3, ChronoUnit.HOURS);

        Optional<Interval> firstFreeTimeslot = DateTimeUtils.findFirstFreeTimeslot(bounds, usersBusyTimeslots, eventDuration);
        assertThat(firstFreeTimeslot, isEmpty());
    }
}
