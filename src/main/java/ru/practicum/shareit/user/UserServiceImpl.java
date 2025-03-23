package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplacateDateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.util.Collection;
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
                .map(o -> UserMapper.toUserDto(o))
                .collect(Collectors.toList());

    }


    @Override
    public UserDto addUser(UserDto request) {
        if (checkUserValidDate(request) == true) {
            User user = userDao.addUser(UserMapper.toUser(request));
            return UserMapper.toUserDto(user);
        }
        return null;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userDao.getUserById(id).orElseThrow(() -> new NotFoundException("User not found " + id));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userUpdateRequest) {
        Optional<User> alreadyExistUser = userDao.findByEmail(userUpdateRequest.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplacateDateException("Данный имейл уже используется");
        }
        User user = userDao.getUserById(id).orElseThrow(() -> new NotFoundException("User not found " + id));
        return UserMapper.toUserDto(userDao.updateUser(user, userUpdateRequest));

    }

    public boolean checkUserValidDate(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new ValidationException("Имейл должен быть указан");
        }

        Optional<User> alreadyExistUser = userDao.findByEmail(userDto.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplacateDateException("Данный имейл уже используется");
        }
        return true;
    }

    @Override
    public Boolean deleteAllUsers() {
        userDao.deleteAllUsers();
        return true;
    }

    @Override
    public Boolean deleteUser(Long id) {
        return userDao.deleteUser(userDao.getUserById(id).orElseThrow(() -> new NotFoundException("User not found " + id)));
    }
}