package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreateRequest;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> findAllUsers();

    UserDto addUser(UserDtoCreateRequest userDtoCreateRequest);

    UserDto getUserById(Long id);

    UserDto updateUser(Long id, UserDto user);

    Boolean deleteAllUsers();

    void deleteUser(Long id);
}
