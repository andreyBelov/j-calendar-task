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
    public void saveCalendar(Long userId, CreateCalendarCommand createUserCommand) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            Calendar calendar = calendarMapper.map(createUserCommand);
            calendar.setUser(userOpt.get());
            calendarRepository.save(calendar);
        } else {
            throw new EntityNotFoundException("User with id = " + userId + " is not found");
        }
    }

    public Page<Calendar> findCalendars(Pageable pageable) {
        return calendarRepository.findAll(pageable);
    }
}
