package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemExtendDto toItemDto(Item item, Booking lastBooking, Booking nextBooking, List<CommentDto> comments) {
        return new ItemExtendDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBooking != null ? BookingMapper.toBookingDto(lastBooking) : null,
                nextBooking != null ? BookingMapper.toBookingDto(nextBooking) : null,
                comments,
                item.getRequest() != null ? item.getRequest().getId() : 0
        );
    }

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

    public static ItemDtoRequest toItemDtoRequest(Item item) {
        return new ItemDtoRequest(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                item.getRequest().getId());

    }

    public static ItemDtoRequest toItemDtoRequest(Item item, Long itemRequestId) {
        return new ItemDtoRequest(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getOwner().getId(),
                itemRequestId);
    }

}
