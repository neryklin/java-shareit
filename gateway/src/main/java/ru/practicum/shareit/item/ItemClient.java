package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.common.StateBooking;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }




    public ResponseEntity<Object> findAllItemByText(Long userId, String text) {
        Map<String, Object> parameters = Map.of("text", text);
        return get("/search?text={text}", userId, parameters);
    }

    public ResponseEntity<Object> getItemById(Long userId, @Min(0) Long id) {
        return get("/" + id, userId);
    }

    public ResponseEntity<Object> getItemByUserId(Long userId) {
        return get("", userId);
    }

    public ResponseEntity<Object> addItem(Long userId, @Valid ItemDtoCreateRequest itemDtoCreateRequest) {
        return post("", userId, itemDtoCreateRequest);
    }


    public ResponseEntity<Object> updateItem(Long userId, @Min(0) Long id, @Valid ItemDto updateUserRequest) {
        return patch("/" + id, userId, updateUserRequest);
    }

    public ResponseEntity<Object> addCommentToItem(long authorId, @Min(0) long itemId, @Valid CommentDto dto) {
        return post("/" + itemId + "/comment", authorId, dto);
    }
}

