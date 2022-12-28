package com.joom.calendar.event;

import com.joom.calendar.calendar.Calendar;
import com.joom.calendar.calendar.CalendarRepository;
import com.joom.calendar.user.User;
import com.joom.calendar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;

    public Page<Event> findEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
    public void createEvent(Long calendarId, CreateEventCommand createEventCommand) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(calendarId);
        if (calendarOpt.isPresent()) {
            Event event = eventMapper.map(createEventCommand);
            event.setCalendar(calendarOpt.get());
            addInviteesToEvent(event, createEventCommand.getInviteeIds());
            eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Calendar with id = " + calendarId + " is not found");
        }
    }

    private void addInviteesToEvent(Event event, Set<Long> inviteeIds) {
        List<User> users = userRepository.findAllById(inviteeIds);
        if (inviteeIds.size() != users.size()) {
            throw new IllegalArgumentException("One or more users not found by this ids: " + inviteeIds);
        }
        event.addInvitees(users);
    }
}
