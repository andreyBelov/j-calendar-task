package com.joom.calendar.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User saveUser(CreateUserCommand createUserCommand) {
        User user = userMapper.map(createUserCommand);
        return userRepository.save(user);
    }

    public Page<User> findUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
