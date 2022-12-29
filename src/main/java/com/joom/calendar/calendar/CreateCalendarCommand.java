package com.joom.calendar.calendar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CreateCalendarCommand {
    @Schema(type = "integer", required = true, example = "3")
    Long userId;
    @Schema(type = "string", required = true, example = "Рабочий")
    String name;
}
