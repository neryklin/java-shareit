package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {
    private final long id;
    private final String text;
    private final ItemDto item;
    private final String authorName;
    private final LocalDateTime created;
}
