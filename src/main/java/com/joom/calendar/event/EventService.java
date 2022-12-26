package com.joom.calendar.event;

import com.joom.calendar.calendar.Calendar;
import com.joom.calendar.calendar.CalendarRepository;
import com.joom.calendar.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    private final EventMapper eventMapper;

    public Page<Event> findEvents(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
    public void createEvent(Long calendarId, CreateEventCommand createEventCommand) {
        Optional<Calendar> calendarOpt = calendarRepository.findById(calendarId);
        if (calendarOpt.isPresent()) {
            Event event = eventMapper.map(createEventCommand);
            event.setCalendar(calendarOpt.get());
            eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Calendar with id = " + calendarId + " is not found");
        }
    }
}
