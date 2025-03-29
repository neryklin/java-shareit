package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import ru.practicum.shareit.item.dto.ItemDtoRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestrDtoCreateRequest;
import ru.practicum.shareit.request.model.ItemRequest;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemRequestMapper {
    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        if (itemRequest == null) {
            return null;
        }
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                UserMapper.toUserDto(itemRequest.getRequestor()),
                itemRequest.getCreated(),
                new ArrayList<ItemDtoRequest>());

    }

    public static ItemRequest toItemRequest(ItemRequestrDtoCreateRequest itemRequestrDtoCreateRequest , User requestor) {
        return new ItemRequest(
                0,
                itemRequestrDtoCreateRequest.getDescription(),
                requestor,
                LocalDateTime.now());
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<ItemDtoRequest> items) {
        return new ItemRequestDto(
                itemRequest.getId(),
                itemRequest.getDescription(),
                UserMapper.toUserDto(itemRequest.getRequestor()),
               itemRequest.getCreated(),
                items);
    }

}
