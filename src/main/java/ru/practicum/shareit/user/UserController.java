package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@Slf4j
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Collection<UserDto> users() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto users(@PathVariable @Min(0) Long id) {
        return userService.getUserById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto newUserRequest) {
        log.info("start create user: {}", newUserRequest);
        return userService.addUser(newUserRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Min(0) Long id) {
        log.info("start delete user: {}", id);
        userService.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable @Min(0) Long id, @Valid @RequestBody UserDto updateUserRequest) {
        log.info("start update user: {}", id);
        return userService.updateUser(id, updateUserRequest);
    }
}
