package ru.practicum.shareit.booking.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.common.StatusBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private long id;
    @Future
    private LocalDateTime start;
    @Future
    private LocalDateTime end;
    @NotNull
    private Item item;
    @NotNull
    private User booker;
    @NotNull
    private StatusBooking status;
}
