package com.joom.calendar.event;

import com.joom.calendar.invitee.Invitee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

public class EventSearchSpecs {

    public static Specification<Event> byUserId(Long userId) {
        return (root, query, cb) -> {
            Join<Invitee, Event> invitee = root.join("invitees");
            return cb.equal(invitee.get("user").get("id"), userId);
        };
    }

    public static Specification<Event> byBounds(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            Predicate oneTime = cb.and(
                    cb.isNull(root.get("cron")),
                    cb.lessThan(root.get("startDateTime"), to),
                    cb.greaterThanOrEqualTo(root.get("endDateTime"), from));
            Predicate repeatableByCron = cb.and(
                    cb.isNotNull(root.get("cron")),
                    cb.lessThan(root.get("startDateTime"), to)
            );
            return cb.or(oneTime, repeatableByCron);
        };
    }
}
