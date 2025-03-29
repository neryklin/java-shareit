package ru.practicum.shareit.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.common.StatusBooking;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDtoCreateRequest {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private long itemId;
    private UserDto booker;
    private StatusBooking status;
}