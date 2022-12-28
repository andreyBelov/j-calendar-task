package com.joom.calendar.event;

import com.joom.calendar.invitee.Answer;
import lombok.Value;

@Value
public class SaveAnswerCommand {
    Long userId;
    Answer answer;
}
