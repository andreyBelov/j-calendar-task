package com.joom.calendar.user;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User map(CreateUserCommand createUserCommand);
}
