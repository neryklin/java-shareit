package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface ItemService {
    Collection<ItemDao> findAllItem();

    UserDto addItem(ItemDto itemDto);

    UserDto getItemById(Long id);

    UserDto updateItem(Long id, ItemDto itemDto);

    Boolean deleteAllItem();

    Boolean deleteItem(Long id);
}
