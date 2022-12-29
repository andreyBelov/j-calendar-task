package com.joom.calendar.event;

import com.joom.calendar.invitee.Answer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Value
public class SaveAnswerCommand {
    @Schema(type="integer" , example = "2")
    Long userId;
    @Schema(type="string", implementation = Answer.class, example = "ACCEPTED")
    Answer answer;
}
