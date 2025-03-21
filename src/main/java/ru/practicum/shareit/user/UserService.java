package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> findAllUsers();

    UserDto addUser(UserDto user);

    UserDto getUserById(Long id);

    User updateUser(User updUser);

    Boolean deleteAllUsers();

    Boolean deleteUser(Long id);
}
