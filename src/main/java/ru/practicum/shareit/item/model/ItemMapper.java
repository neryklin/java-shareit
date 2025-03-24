package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : 0
        );
    }

    public static Item toItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                new User(),
                null
        );
    }

    public static Item toItem(ItemDtoCreateRequest itemDtoCreateRequest) {
        return new Item(
                itemDtoCreateRequest.getId(),
                itemDtoCreateRequest.getName(),
                itemDtoCreateRequest.getDescription(),
                itemDtoCreateRequest.getAvailable(),
                new User(),
                itemDtoCreateRequest.getRequest()

        );
    }
}
