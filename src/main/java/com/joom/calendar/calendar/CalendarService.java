package com.joom.calendar.calendar;

import com.joom.calendar.user.User;
import com.joom.calendar.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final CalendarMapper calendarMapper;

    @Transactional
    public CalendarDetailsDto saveCalendar(CreateCalendarCommand createUserCommand) {
        Optional<User> userOpt = userRepository.findById(createUserCommand.getUserId());
        if (userOpt.isPresent()) {
            Calendar calendar = calendarMapper.map(createUserCommand);
            calendar.setUser(userOpt.get());
            Calendar savedCalendar = calendarRepository.save(calendar);
            return calendarMapper.map(savedCalendar);
        } else {
            throw new EntityNotFoundException("User with id = " + createUserCommand.getUserId() + " is not found");
        }
    }

    public Page<Calendar> findCalendars(Pageable pageable) {
        return calendarRepository.findAll(pageable);
    }
}
