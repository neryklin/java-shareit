package ru.practicum.shareit.request.dto;

import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;



@Data
public class ItemRequestDto {
    private long id;
    private String description;
    private UserDto requestor;
}
