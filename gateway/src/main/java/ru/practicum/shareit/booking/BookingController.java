package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.common.StateBooking;


@RestController
@RequestMapping("/bookings")
@Slf4j
@AllArgsConstructor
@Validated
public class BookingController {

    private final BookingClient bookingClient;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody BookingDtoCreateRequest bookingDtoCreateRequest, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("start create booking: {}", bookingDtoCreateRequest);
        return bookingClient.create(userId, bookingDtoCreateRequest);
    }

    @Validated
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> setBookingApproval(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                     @PathVariable Long bookingId,
                                                     @RequestParam @NotNull Boolean approved) {
        log.info("start aproove booking: {}", bookingId);
        return bookingClient.setApproval(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                 @PathVariable Long bookingId) {
        log.info("start getBookingById: {}", bookingId);
        return bookingClient.getBookingById(userId, bookingId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                        @RequestParam(required = false) StateBooking stateBooking) {
        stateBooking = stateBooking == null ? StateBooking.ALL : stateBooking;
        log.info("start getAllBookingsByOwner booking: {}", userId);
        return bookingClient.getAllByOwnerAndState(userId, stateBooking);
    }

    @GetMapping
    public ResponseEntity<Object> getBookings(@RequestHeader(name = "X-Sharer-User-Id") long bookerId,
                                              @RequestParam(defaultValue = "ALL") StateBooking state) {
        return bookingClient.getAllByBookerAndState(bookerId, state);
    }
}
