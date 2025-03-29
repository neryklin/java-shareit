package ru.practicum.shareit.request.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestrDtoCreateRequest {
    private String description;
}
