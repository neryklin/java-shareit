package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestrDtoCreateRequest;


import java.util.Collection;

public interface ItemRequestService {


    Collection<ItemRequestDto> getAllItemRequestsByRequestor(Long userId);

    Collection<ItemRequestDto> getAllItemRequests();

    ItemRequestDto getItemRequestById(Long requestId);

    ItemRequestDto addItemRequest(Long userId, ItemRequestrDtoCreateRequest itemRequestrDtoCreateRequest);
}
