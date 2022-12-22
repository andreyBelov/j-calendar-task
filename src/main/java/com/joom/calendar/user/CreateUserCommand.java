package com.joom.calendar.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class CreateUserCommand {
    @Schema(type = "string", required = true, example = "Andrey Beloborodov")
    String name;
}
