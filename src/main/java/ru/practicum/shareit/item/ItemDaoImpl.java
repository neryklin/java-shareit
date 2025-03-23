package ru.practicum.shareit.item;


import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ItemDaoImpl implements ItemDao {
    final List<Item> itemList = new ArrayList<>();

    @Override
    public Collection<Item> findAllItem() {
        return itemList;
    }

    @Override
    public Collection<Item> findAllItemByText(String stringSearch) {
        return itemList.stream()
                .filter(o -> o.getAvailable())
                .filter(o -> (o.getName() + o.getDescription()).toUpperCase().contains(stringSearch.toUpperCase()))
                .collect(Collectors.toList());

    }

    @Override
    public Item addItem(User user, Item item) {
        itemList.add(item);
        item.setId(getNextId());
        item.setOwner(user);
        return item;
    }

    @Override
    public Optional<Item> getItemById(Long id) {
        Optional<Item> optionalItem = itemList.stream().filter(o -> o.getId() == id).findFirst();
        return optionalItem;
    }

    @Override
    public Collection<Item> findAllItemByUserId(Long userId) {
        return itemList.stream()
                .filter(o -> o.getOwner().getId() == userId)
                .collect(Collectors.toList());
    }

    @Override
    public Item updateItem(User user, Item item, ItemDto updateItemDto) {
        if (!itemList.contains(item)) {
            itemList.add(item);
        }
        item.setName(updateItemDto.getName() == null ? item.getName() : updateItemDto.getName());
        item.setDescription(updateItemDto.getDescription() == null ? item.getDescription() : updateItemDto.getDescription());
        item.setAvailable(updateItemDto.getAvailable() == null ? item.getAvailable() : updateItemDto.getAvailable());
        item.setOwner(user);
        return item;
    }

    @Override
    public void deleteAllItems() {
        itemList.clear();
    }

    @Override
    public Boolean deleteItem(Item item) {
        return itemList.remove(item);
    }

    public long getNextId() {
        long currentMaxId = itemList
                .stream()
                .mapToLong(o -> o.getId())
                .max()
                .orElse(0);
        return ++currentMaxId;

    }
}
