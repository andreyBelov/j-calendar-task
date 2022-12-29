package com.joom.calendar.user;

import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public Page<User> findAllUsers(@ParameterObject Pageable pageable) {
        return userService.findUsers(pageable);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserCommand createUserCommand) {
        return userService.saveUser(createUserCommand);
    }

}
