package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDto> findAllItem();

    Collection<ItemDto> getItemByUserId(Long userId);

    Collection<ItemDto> findAllItemByText(String searchText);

    ItemDto addItem(Long userId, ItemDtoCreateRequest itemDtoCreateRequest);

    ItemDto getItemById(Long id);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    Boolean deleteAllItem();

    Boolean deleteItem(Long id);
}
