package ru.practicum.shareit.user;


import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserDao {
    Collection<User> findAllUsers();

    User addUser(User user);

    Optional<User> getUserById(Long id);

    User updateUser(User updUser);

    void deleteAllUsers();

    Boolean deleteUser(User user);

    Optional<User> findByEmail(String email);
}
