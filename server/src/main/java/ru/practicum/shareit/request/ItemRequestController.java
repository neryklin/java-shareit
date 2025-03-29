package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestrDtoCreateRequest;

import java.util.Collection;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRequestDto addItemRequest(@RequestBody ItemRequestrDtoCreateRequest itemRequestrDtoCreateRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("добавление вещи: '{}' пользователя с id = {}", itemRequestrDtoCreateRequest, userId);
        return itemRequestService.addItemRequest(userId, itemRequestrDtoCreateRequest);
    }

    @GetMapping
    public Collection<ItemRequestDto> getAllItemRequestsByRequestor(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Cписко всех запросов пользователя с id = {}", userId);
        return itemRequestService.getAllItemRequestsByRequestor(userId);
    }

    @GetMapping("/all")
    public Collection<ItemRequestDto> getAllItemRequests() {
        log.info("списка всех запросов");
        return itemRequestService.getAllItemRequests();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequestById(@PathVariable Long requestId) {
        log.info("запроса с id = {}", requestId);
        return itemRequestService.getItemRequestById(requestId);
    }
}
