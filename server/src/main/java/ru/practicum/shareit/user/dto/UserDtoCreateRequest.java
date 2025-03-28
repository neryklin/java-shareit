package ru.practicum.shareit.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoCreateRequest {
    private long id;

    private String name;

    private String email;
}
