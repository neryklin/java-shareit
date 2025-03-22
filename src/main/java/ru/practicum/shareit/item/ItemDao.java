package ru.practicum.shareit.item;


import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;


public interface ItemDao {
    Collection<User> findAllUsers();

    User addUser(User user);

    Optional<User> getUserById(Long id);

    User updateUser(User user, UserDto updateUserDto);

    void deleteAllUsers();

    Boolean deleteUser(User user);

    Optional<User> findByEmail(String email);
}
