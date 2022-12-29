package com.joom.calendar.event;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class CronExpressionGenerator {

    public static String mapToCronString(PredefinedSchedule schedule, LocalDateTime startDateTime) {
        if (schedule == null) return null;
        return switch (schedule) {
            case EVERY_DAY -> everyDayCron(startDateTime);
            case EVERY_WEEK -> everyWeekCron(startDateTime);
            case DAY_OF_NTH_WEEK_OF_EVERY_MONTH -> dayOfNthWeekEveryMonthCron(startDateTime);
            case EVERY_YEAR -> everyYearCron(startDateTime);
            case EVERY_WEEKDAY -> everyWeekday(startDateTime);
        };
    }

    private static String everyDayCron(LocalDateTime s) {
        return String.join(" ",
                String.valueOf(s.getSecond()),
                String.valueOf(s.getMinute()),
                String.valueOf(s.getHour()),
                "*",
                "*",
                "?"
        );
    }

    private static String everyWeekCron(LocalDateTime s) {
        return String.join(" ",
                String.valueOf(s.getSecond()),
                String.valueOf(s.getMinute()),
                String.valueOf(s.getHour()),
                "?",
                "*",
                String.valueOf(s.getDayOfWeek().getValue())
        );
    }

    private static String dayOfNthWeekEveryMonthCron(LocalDateTime s) {
        // TODO: honor locale (week can start from sunday or monday)
        TemporalField wom = WeekFields.of(Locale.getDefault()).weekOfMonth();
        int weekOfMonth = s.get(wom);

        return String.join(" ",
                String.valueOf(s.getSecond()),
                String.valueOf(s.getMinute()),
                String.valueOf(s.getHour()),
                "?",
                "*",
                s.getDayOfWeek().getValue() + "#" + weekOfMonth
        );
    }

    private static String everyYearCron(LocalDateTime s) {
        return String.join(" ",
                String.valueOf(s.getSecond()),
                String.valueOf(s.getMinute()),
                String.valueOf(s.getHour()),
                String.valueOf(s.getDayOfMonth()),
                String.valueOf(s.getMonthValue()),
                "?"
        );
    }

    private static String everyWeekday(LocalDateTime s) {
        return String.join(" ",
                String.valueOf(s.getSecond()),
                String.valueOf(s.getMinute()),
                String.valueOf(s.getHour()),
                "?",
                String.valueOf(s.getMonthValue()),
                "MON-FRI"
        );
    }
}
