package ru.practicum.shareit.user;


import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    final List<User> usersList = new ArrayList<>();


    @Override
    public Collection<User> findAllUsers() {
        return usersList;
    }

    @Override
    public User addUser(User user) {
        usersList.add(user);
        user.setId(getNextId());
        return user;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        Optional<User> optionalUser = usersList.stream().filter(o -> o.getId() == id).findFirst();
        return optionalUser;
    }

    @Override
    public User updateUser(User user, UserDto updateUserDto) {

        if (usersList.contains(user)) {
        } else {
            usersList.add(user);
        }

        user.setEmail(updateUserDto.getEmail() == null ? user.getEmail() : updateUserDto.getEmail());
        user.setName(updateUserDto.getName() == null ? user.getName() : updateUserDto.getName());
        return user;
    }

    @Override
    public void deleteAllUsers() {
        usersList.clear();
    }

    @Override
    public Boolean deleteUser(User user) {
        return usersList.remove(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = usersList.stream().filter(o -> o.getEmail().equals(email)).findFirst();
        return optionalUser;
    }

    public long getNextId() {
        long currentMaxId = usersList
                .stream()
                .mapToLong(o -> o.getId())
                .max()
                .orElse(0);
        return ++currentMaxId;

    }

}
