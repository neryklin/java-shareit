package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreateRequest;

import java.util.Map;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/users";

    @Autowired
    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }


    public ResponseEntity<Object> updateUser(@Min(0) Long id, @Valid UserDto updateUserRequest) {
        return patch("/" + id, id, updateUserRequest);
    }

    public ResponseEntity<Object> deleteUser(@Min(0) Long id) {
        return delete("/" + id, id);
    }

    public ResponseEntity<Object> addUser(@Valid UserDtoCreateRequest userDtoCreateRequest) {
        return post("", userDtoCreateRequest);
    }

    public ResponseEntity<Object> getUserById(@Min(0) Long id) {
        return get("/" + id);
    }

    public ResponseEntity<Object> findAllUsers() {
        return get("");
    }
}

