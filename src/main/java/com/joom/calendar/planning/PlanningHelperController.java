package com.joom.calendar.planning;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.extra.Interval;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlanningHelperController {

    private final PlanningHelperService planningHelperService;

    @GetMapping("/first-free-timeslot")
    public Interval findFirstFreeTimeslot(@Parameter(example = "[2,3]") @RequestParam List<Long> userIds,
                                          @Parameter(example = "60") @RequestParam int eventDurationInMinutes,
                                          @Parameter(example = "2022-12-27T09:00Z") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime leftBound,
                                          @Parameter(example = "2022-12-27T18:00Z") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mmX") LocalDateTime rightBound) {
        return planningHelperService.findFirstFreeTimeslot(userIds, eventDurationInMinutes, leftBound, rightBound);
    }

}
