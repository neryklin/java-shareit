package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> findAllUsers();

    UserDto addUser(UserDto user);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto user);

    Boolean deleteAllUsers();

    Boolean deleteUser(Long id);
}
