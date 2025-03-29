package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.model.UserMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingMapper {

    public static BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                ItemMapper.toItemDto(booking.getItem()),
                UserMapper.toUserDto(booking.getBooker()),
                booking.getStatus()
        );
    }

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                ItemMapper.toItem(bookingDto.getItem()),
                UserMapper.toUser(bookingDto.getBooker()),
                bookingDto.getStatus()
        );
    }

    public static Booking toBooking(BookingDtoCreateRequest bookingDtoCreateRequest) {
        return new Booking(
                bookingDtoCreateRequest.getId() == null ? 0 : bookingDtoCreateRequest.getId(),
                bookingDtoCreateRequest.getStart(),
                bookingDtoCreateRequest.getEnd(),
                new Item(),
                new User(),
                bookingDtoCreateRequest.getStatus()
        );
    }
}
