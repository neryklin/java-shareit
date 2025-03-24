package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplacateDateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreateRequest;
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

    private final UserRepository userRepository;



    @Override
    public Collection<UserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }


    @Override
    public UserDto addUser(UserDtoCreateRequest userDtoCreateRequest) {
        Optional<User> alreadyExistUser = userRepository.findByEmail(userDtoCreateRequest.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplacateDateException("Данный имейл уже используется");
        }
        User user = UserMapper.toUser(userDtoCreateRequest);
        user = userRepository.save(user);
        return UserMapper.toUserDto(user);
    }


    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found " + id));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userUpdateRequest) {
        Optional<User> alreadyExistUser = userRepository.findByEmail(userUpdateRequest.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplacateDateException("Данный имейл уже используется");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found " + id));

        user.setName(userUpdateRequest.getName()==null ? user.getName() : userUpdateRequest.getName());
        user.setEmail(userUpdateRequest.getEmail()==null ? user.getEmail() : userUpdateRequest.getEmail());
        return UserMapper.toUserDto(userRepository.save(user));

    }

    public boolean checkUserValidDate(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
            throw new ValidationException("Имейл должен быть указан");
        }

        Optional<User> alreadyExistUser = userRepository.findByEmail(userDto.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new DuplacateDateException("Данный имейл уже используется");
        }
        return true;
    }

    @Override
    public Boolean deleteAllUsers() {
        userRepository.deleteAll();
        return true;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }


}