package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserDao;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Класс реализации запросов к информации о пользователях
 */
@Slf4j
@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemDao itemDao;
    private final UserDao userDao;

    @Override
    public Collection<ItemDto> findAllItem() {
        return itemDao.findAllItem().stream()
                .map(o -> ItemMapper.toItemDto(o))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItemByUserId(Long userId) {
        return itemDao.findAllItemByUserId(userId).stream()
                .map(o -> ItemMapper.toItemDto(o))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findAllItemByText(String searchText) {
        if (searchText.isEmpty()) {
            return new ArrayList<ItemDto>();
        }
        return itemDao.findAllItemByText(searchText).stream()
                .map(o -> ItemMapper.toItemDto(o))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(Long userId, ItemDto itemDto) {
        User user = userDao.getUserById(userId).orElseThrow(() -> new NotFoundException("User not found " + userId));
        if (checkUserValidDate(itemDto) == true) {
            Item item = itemDao.addItem(user, ItemMapper.toItem(itemDto));
            return ItemMapper.toItemDto(item);
        }
        return null;
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemDao.getItemById(id).orElseThrow(() -> new NotFoundException("Item not found " + id));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        User user = userDao.getUserById(userId).orElseThrow(() -> new NotFoundException("User not found " + userId));
        Item item = itemDao.getItemById(itemId).orElseThrow(() -> new NotFoundException("Item not found " + itemId));
        return ItemMapper.toItemDto(itemDao.updateItem(user, item, itemDto));

    }

    @Override
    public Boolean deleteAllItem() {
        itemDao.deleteAllItems();
        return true;
    }

    @Override
    public Boolean deleteItem(Long id) {
        itemDao.deleteItem(itemDao.getItemById(id).get());
        return true;
    }

    public boolean checkUserValidDate(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isEmpty()) {
            throw new ValidationException("Название должен быть указан");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isEmpty()) {
            throw new ValidationException("Описание должен быть указан");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Доступность должена быть указана");
        }
        return true;
    }
}