package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.common.StateBooking;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingService {

    BookingDto create(Long userId, BookingDtoCreateRequest bookingDtoCreateRequest);

    BookingDto setApproval(Long userId, Long bookingId, Boolean approved);

    Optional<BookingDto> getBookingById(Long userId, Long bookingId);

    List<BookingDto> getAllByOwnerAndState(Long userId, StateBooking status);

    Collection<BookingDto> getAllByBookerAndState(long bookerId, StateBooking state);
}
