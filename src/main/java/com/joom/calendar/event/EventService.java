package com.joom.calendar.event;

import com.joom.calendar.calendar.Calendar;
import com.joom.calendar.calendar.CalendarRepository;
import com.joom.calendar.invitee.Invitee;
import com.joom.calendar.invitee.InviteeId;
import com.joom.calendar.invitee.InviteeRepository;
import com.joom.calendar.user.User;
import com.joom.calendar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.joom.calendar.event.EventSearchSpecs.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final InviteeRepository inviteeRepository;
    private final EventMapper eventMapper;

    private final CronEventEvaluator cronEventEvaluator;

    public List<EventDetailsDto> findEventsByUserIdAndBounds(Long userId, LocalDateTime left, LocalDateTime right) {
        Specification<Event> byUserIdAndBounds = byUserIdEqualTo(userId)
                .and(byBounds(left, right));
        return eventRepository.findAll(byUserIdAndBounds).stream()
                .flatMap(e -> cronEventEvaluator.generateOnTheIntervalAndMap(e, left, right).stream())
                .toList();
    }

    public EventDetailsDto findById(Long id) {
        return eventRepository.findById(id)
                .map(eventMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Event with id = " + id + " is not found"));
    }

    @Transactional
    public EventDetailsDto createEvent(CreateEventCommand cec) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(cec.getCalendarId());
        if (calendarOpt.isPresent()) {
            Event event = eventMapper.map(cec);
            event.setCalendar(calendarOpt.get());
            addInviteesToEvent(event, cec.getInviteeIds());
            Event savedEvent = eventRepository.save(event);
            return eventMapper.map(savedEvent);
        } else {
            throw new EntityNotFoundException("Calendar with id = " + cec.getCalendarId() + " is not found");
        }
    }

    @Transactional
    public void saveAnswer(Long eventId, SaveAnswerCommand sac) {
        Invitee invitee = inviteeRepository.findById(new InviteeId(sac.getUserId(), eventId))
                .orElseThrow(() -> new EntityNotFoundException("Invitee with eventId = " + eventId + " and userId = " + sac.getUserId() + " is not found"));
        invitee.setAnswer(sac.getAnswer());
    }

    private void addInviteesToEvent(Event event, Set<Long> inviteeIds) {
        List<User> users = userRepository.findAllById(inviteeIds);
        if (inviteeIds.size() != users.size()) {
            throw new IllegalArgumentException("One or more users not found by this ids: " + inviteeIds);
        }
        event.addInvitees(users);
    }
}
