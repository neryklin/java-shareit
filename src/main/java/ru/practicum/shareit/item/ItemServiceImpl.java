package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

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
public class ItemServiceImpl implements ItemService {

private final ItemDao userDao;


    @Override
    public Collection<UserDto> findAllUsers() {
        return userDao.findAllUsers().stream()
                .map(o->UserMapper.toUserDto(o))
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
        User user = userDao.getUserById(id).orElseThrow(()-> new NotFoundException("User not found " + id));
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userUpdateRequest) {
        Optional<User> alreadyExistUser = userDao.findByEmail(userUpdateRequest.getEmail());
        if (alreadyExistUser.isPresent()) {
            throw new ValidationException("Данный имейл уже используется");
        }
            User user = userDao.getUserById(id).orElseThrow(()-> new NotFoundException("User not found " + id));
            return UserMapper.toUserDto(userDao.updateUser(user,userUpdateRequest));

    }

public boolean checkUserValidDate(UserDto userDto){
    if (userDto.getEmail() == null || userDto.getEmail().isEmpty()) {
        throw new ValidationException("Имейл должен быть указан");
    }

    Optional<User> alreadyExistUser = userDao.findByEmail(userDto.getEmail());
    if (alreadyExistUser.isPresent()) {
        throw new ValidationException("Данный имейл уже используется");
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
        return userDao.deleteUser(userDao.getUserById(id).orElseThrow(()-> new NotFoundException("User not found " + id)));
    }

    @Override
    public Collection<ItemDao> findAllItem() {
        return List.of();
    }

    @Override
    public UserDto addItem(ItemDto itemDto) {
        return null;
    }

    @Override
    public UserDto getItemById(Long id) {
        return null;
    }

    @Override
    public UserDto updateItem(Long id, ItemDto itemDto) {
        return null;
    }

    @Override
    public Boolean deleteAllItem() {
        return null;
    }

    @Override
    public Boolean deleteItem(Long id) {
        return null;
    }
}