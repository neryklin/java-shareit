package ru.practicum.shareit.item;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;
import ru.practicum.shareit.item.dto.ItemExtendDto;

import java.util.Collection;


@RestController
@RequestMapping("/items")
@Slf4j
@AllArgsConstructor
@Validated
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/all")
    public Collection<ItemDto> items() {
        return itemService.findAllItem();
    }

    @GetMapping("/search")
    public Collection<ItemDto> items(@RequestParam String text) {
        return itemService.findAllItemByText(text);
    }

    @GetMapping("/{id}")
    public ItemExtendDto itemsSearch(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long id) {
        return itemService.getItemById(userId, id);
    }


    @GetMapping
    public Collection<ItemDto> itemsUsers(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItemByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestBody ItemDtoCreateRequest itemDtoCreateRequest,
                          @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("start create item: {}", itemDtoCreateRequest);
        return itemService.addItem(userId, itemDtoCreateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable  Long id) {
        log.info("start delete user: {}", id);
        itemService.deleteItem(id);
    }

    @PatchMapping("/{id}")
    public ItemDto updateUser(@PathVariable  Long id,
                              @RequestHeader("X-Sharer-User-Id") Long userId,
                              @RequestBody ItemDto updateUserRequest) {
        log.info("start update item: {}", id);
        return itemService.updateItem(userId, id, updateUserRequest);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addCommentToItem(@PathVariable  long itemId,
                                       @RequestHeader(name = "X-Sharer-User-Id") long authorId,
                                       @RequestBody  CommentDto dto) {
        return itemService.addCommentToItem(authorId, itemId, dto);
    }

}
