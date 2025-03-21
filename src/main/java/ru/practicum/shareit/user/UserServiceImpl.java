package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализации запросов к информации о пользователях
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

private final UserDao userDao;


    @Override
    public Collection<UserDto> findAllUsers() {
        return userDao.findAllUsers().stream()
                .map(o->UserMapper.toUserDto(o))
                .collect(Collectors.toList());

    }


    @Override
    public UserDto addUser(UserDto request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new ValidationException("Имейл должен быть указан");
        }

        Optional<User> alreadyExistUser = userDao.findByEmail(request.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new ValidationException("Данный имейл уже используется");
        }
        User user = userDao.addUser(UserMapper.toUser(request));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userDao.getUserById(id).orElseThrow(()-> new NotFoundException("User not found " + id));
        return UserMapper.toUserDto(user);
    }

    @Override
    public User updateUser(User updUser) {
        return null;
    }

    @Override
    public Boolean deleteAllUsers() {
        userDao.deleteAllUsers();
        return true;
    }

    @Override
    public Boolean deleteUser(Long id) {
        return userDao.deleteUser(userDao.getUserById(id).orElseThrow(()-> new NotFoundException("User not found " + id)));
    }
}