package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoCreateRequest {
    private long id;

    private String name;

    private String description;

    private Boolean available;

    private ItemRequest request;

    private Long requestId;

}