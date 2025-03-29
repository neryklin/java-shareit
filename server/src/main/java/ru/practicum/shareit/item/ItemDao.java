package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;
import java.util.Optional;


public interface ItemDao {
    Collection<Item> findAllItem();

    Collection<Item> findAllItemByText(String string);

    Item addItem(User user, Item item);

    Optional<Item> getItemById(Long id);

    Collection<Item> findAllItemByUserId(Long userId);

    Item updateItem(User user, Item item, ItemDto updateItemDto);

    void deleteAllItems();

    Boolean deleteItem(Item item);


}
