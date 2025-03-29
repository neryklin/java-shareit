package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createItemRequest(@Valid @RequestBody ItemRequestDto dto,
                                                    @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("добавление нового запроса  '{}' пользователя с id = {}", dto, userId);
        return itemRequestClient.createItemRequest(userId, dto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllItemRequestsByRequestor(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Cписок всех запросов пользователя с id = {}", userId);
        return itemRequestClient.getAllItemRequestsByRequestor(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllItemRequests() {
        log.info("Получение  всех запросов");
        return itemRequestClient.getAllItemRequests();
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequestById(@PathVariable Long requestId) {
        log.info("Запрос с id = {}", requestId);
        return itemRequestClient.getItemRequestById(requestId);
    }
}
