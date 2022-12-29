package com.joom.calendar;

import com.joom.calendar.calendar.Calendar;
import com.joom.calendar.calendar.CalendarService;
import com.joom.calendar.calendar.CreateCalendarCommand;
import com.joom.calendar.event.*;
import com.joom.calendar.invitee.Answer;
import com.joom.calendar.planning.PlanningHelperService;
import com.joom.calendar.user.CreateUserCommand;
import com.joom.calendar.user.User;
import com.joom.calendar.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.threeten.extra.Interval;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class BaseDataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final CalendarService calendarService;
    private final EventService eventService;

    private final PlanningHelperService planningHelperService;

    @Override
    public void run(String... args) {
        userService.saveUser(new CreateUserCommand("Andrey Beloborodov"));
        userService.saveUser(new CreateUserCommand("Vasya Pupkin"));

        calendarService.saveCalendar(new CreateCalendarCommand(1L, "Рабочий"));
        calendarService.saveCalendar(new CreateCalendarCommand(2L, "Рабочий"));

        eventService.createEvent(everyDayFifteenMinutesEventForBothUsers());
        eventService.saveAnswer(1L, new SaveAnswerCommand(2L, Answer.ACCEPTED));

        eventService.createEvent(oneDateFiveHoursEventForVasya());

        Interval firstFreeTimeslot = planningHelperService.findFirstFreeTimeslot(
                List.of(1L, 2L),
                120,
                LocalDateTime.of(2022, Month.DECEMBER, 30, 9, 0),
                LocalDateTime.of(2022, Month.DECEMBER, 30, 18, 0)
        );
        log.info("First free timeslot: {}", firstFreeTimeslot);
    }

    private CreateEventCommand everyDayFifteenMinutesEventForBothUsers() {
        return new CreateEventCommand(
                1L,
                "Обсуждение базового набора данных",
                "Размер базового набора данных",
                "Переговорка 406",
                LocalDateTime.of(2022, Month.DECEMBER, 27, 10, 15),
                LocalDateTime.of(2022, Month.DECEMBER, 27, 10, 30),
                PredefinedSchedule.EVERY_DAY,
                EventVisibility.PUBLIC,
                Set.of(1L, 2L)
        );
    }

    private CreateEventCommand oneDateFiveHoursEventForVasya() {
        return new CreateEventCommand(
                2L,
                "Обсуждение процессов Agile",
                "Читаем и обсуждаем манифест agile",
                "Командный зум",
                LocalDateTime.of(2022, Month.DECEMBER, 27, 13, 0),
                LocalDateTime.of(2022, Month.DECEMBER, 27, 17, 0),
                null,
                EventVisibility.PUBLIC,
                Set.of(2L)
        );
    }
}
