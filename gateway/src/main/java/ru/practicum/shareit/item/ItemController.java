package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;


@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
@Validated
public class ItemController {

    private final ItemClient itemClient;


    @GetMapping("/search")
    public ResponseEntity<Object> items(@RequestHeader("X-Sharer-User-Id") Long userId,
                                        @RequestParam String text) {
        return itemClient.findAllItemByText(userId, text);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> itemsSearch(@RequestHeader("X-Sharer-User-Id") Long userId,
                                              @PathVariable @Min(0) Long id) {
        return itemClient.getItemById(userId, id);
    }


    @GetMapping
    public ResponseEntity<Object> itemsUsers(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getItemByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDtoCreateRequest itemDtoCreateRequest,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("start create item: {}", itemDtoCreateRequest);
        return itemClient.addItem(userId, itemDtoCreateRequest);
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<Object> delete(@PathVariable @Min(0) Long id) {
//        log.info("start delete user: {}", id);
//        return itemClient.deleteItem(id);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable @Min(0) Long id,
                                             @RequestHeader("X-Sharer-User-Id") Long userId,
                                             @Valid @RequestBody ItemDto updateUserRequest) {
        log.info("start update item: {}", id);
        return itemClient.updateItem(userId, id, updateUserRequest);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addCommentToItem(@PathVariable @Min(0) long itemId,
                                                   @RequestHeader(name = "X-Sharer-User-Id") long authorId,
                                                   @RequestBody @Valid CommentDto dto) {
        return itemClient.addCommentToItem(authorId, itemId, dto);
    }

}
