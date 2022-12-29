package com.joom.calendar.event;

import com.joom.calendar.invitee.Invitee;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

public class EventSearchSpecs {

    public static Specification<Event> byUserIdEqualTo(Long userId) {
        return (root, query, cb) -> {
            Join<Invitee, Event> invitee = root.join("invitees");
            return cb.equal(invitee.get("user").get("id"), userId);
        };
    }

    public static Specification<Event> byUserIdIn(List<Long> userIds) {
        return (root, query, cb) -> {
            Join<Object, Object> invitee = (Join<Object, Object>) root.fetch("invitees", JoinType.INNER);
            Join<Object, Object> user = (Join<Object, Object>) invitee.fetch("user", JoinType.LEFT);
            return user.get("id").in(userIds);
        };
    }

    public static Specification<Event> byBounds(LocalDateTime left, LocalDateTime right) {
        return (root, query, cb) -> {
            Predicate oneTime = cb.and(
                    cb.isNull(root.get("cron")),
                    cb.lessThan(root.get("startDateTime"), right),
                    cb.greaterThanOrEqualTo(root.get("endDateTime"), left));
            Predicate repeatableByCron = cb.and(
                    cb.isNotNull(root.get("cron")),
                    cb.lessThan(root.get("startDateTime"), right)
            );
            return cb.or(oneTime, repeatableByCron);
        };
    }
}
