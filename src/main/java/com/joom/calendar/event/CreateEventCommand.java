package com.joom.calendar.event;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Set;

@Value
public class CreateEventCommand {
    @Schema(type = "string", required = true, example = "Обсуждение интерфейса календаря")
    String name;
    @Schema(type = "string", required = true, example = "1. Цвет календаря\n 2. Шрифт")
    String description;
    @Schema(type = "string", required = true, example = "https://joom.zoom.us/j/12312312312")
    String place;
    @Schema(type="string" , pattern = "yyyy-MM-dd'T'HH:mmX", example = "2022-12-22T10:15Z")
    LocalDateTime startDateTime;
    @Schema(type="string" , pattern = "yyyy-MM-dd'T'HH:mmX", example = "2022-12-22T10:30Z")
    LocalDateTime endDateTime;
    PredefinedSchedule predefinedSchedule;
    EventVisibility eventVisibility;
    Set<Long> inviteeIds;
}
