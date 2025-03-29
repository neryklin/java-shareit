package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreateRequest;


@Slf4j
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
@Validated
public class UserController {

    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> users() {
        return userClient.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> users(@PathVariable @Min(0) Long id) {
        return userClient.getUserById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody UserDtoCreateRequest userDtoCreateRequest) {
        log.info("start create user: {}", userDtoCreateRequest);
        return userClient.addUser(userDtoCreateRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> delete(@PathVariable @Min(0) Long id) {
        log.info("start delete user: {}", id);
        return userClient.deleteUser(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Min(0) Long id, @Valid @RequestBody UserDto updateUserRequest) {
        log.info("start update user: {}", id);
        return userClient.updateUser(id, updateUserRequest);
    }
}
