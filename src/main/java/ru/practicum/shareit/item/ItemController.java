package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;

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
    public ItemDto itemsSearch(@PathVariable @Min(0) Long id) {
        return itemService.getItemById(id);
    }


    @GetMapping
    public Collection<ItemDto> itemsUsers(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getItemByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@Valid @RequestBody ItemDtoCreateRequest itemDtoCreateRequest, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("start create item: {}", itemDtoCreateRequest);
        return itemService.addItem(userId, itemDtoCreateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Min(0) Long id) {
        log.info("start delete user: {}", id);
        itemService.deleteItem(id);
    }

    @PatchMapping("/{id}")
    public ItemDto updateUser(@PathVariable @Min(0) Long id, @RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody ItemDto updateUserRequest) {
        log.info("start update item: {}", id);
        return itemService.updateItem(userId, id, updateUserRequest);
    }

}
