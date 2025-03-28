package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.common.StateBooking;

import java.util.Collection;
import java.util.List;


@RestController
@RequestMapping("/bookings")
@Slf4j
@AllArgsConstructor
@Validated
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@Valid @RequestBody BookingDtoCreateRequest bookingDtoCreateRequest, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("start create booking: {}", bookingDtoCreateRequest);
        return bookingService.create(userId, bookingDtoCreateRequest);
    }

    @Validated
    @PatchMapping("/{bookingId}")
    public BookingDto setBookingApproval(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable Long bookingId,
                                         @RequestParam @NotNull Boolean approved) {
        log.info("start aproove booking: {}", bookingId);
        return bookingService.setApproval(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                     @PathVariable Long bookingId) {
        log.info("start getBookingById: {}", bookingId);
        return bookingService.getBookingById(userId, bookingId).get();
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                  @RequestParam(required = false) StateBooking stateBooking) {
        stateBooking = stateBooking == null ? StateBooking.ALL : stateBooking;
        log.info("start getAllBookingsByOwner booking: {}", userId);
        return bookingService.getAllByOwnerAndState(userId, stateBooking);
    }

    @GetMapping
    public Collection<BookingDto> getBookings(@RequestHeader(name = "X-Sharer-User-Id") long bookerId,
                                              @RequestParam(defaultValue = "ALL") StateBooking state) {
        return bookingService.getAllByBookerAndState(bookerId, state);
    }
}
