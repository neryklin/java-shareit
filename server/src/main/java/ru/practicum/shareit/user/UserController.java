package ru.practicum.shareit.user;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreateRequest;

import java.util.Collection;


@Slf4j
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<UserDto> users() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto users(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDtoCreateRequest userDtoCreateRequest) {
        log.info("start create user: {}", userDtoCreateRequest);
        return userService.addUser(userDtoCreateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable  Long id) {
        log.info("start delete user: {}", id);
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable  Long id,  @RequestBody UserDto updateUserRequest) {
        log.info("start update user: {}", id);
        return userService.updateUser(id, updateUserRequest);
    }
}
