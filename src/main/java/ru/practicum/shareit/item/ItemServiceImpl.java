package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.user.UserDao;
import ru.practicum.shareit.user.UserRepository;
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

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public Collection<ItemDto> findAllItem() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> getItemByUserId(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ItemDto> findAllItemByText(String searchText) {
        if (searchText.isEmpty()) {
            return new ArrayList<ItemDto>();
        }
        return itemRepository.findByText(searchText).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto addItem(Long userId, ItemDtoCreateRequest itemDtoCreateRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found " + userId));
        if (checkUserValidDate(itemDtoCreateRequest)) {
            Item item = ItemMapper.toItem(itemDtoCreateRequest);
            item.setOwner(user);
            item.setRequest(itemDtoCreateRequest.getRequest());
            item = itemRepository.save(item);
            return ItemMapper.toItemDto(item);
        }
        return null;
    }

    @Override
    public ItemDto getItemById(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Item not found " + id));
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found " + userId));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found " + itemId));

        item.setName(itemDto.getName()==null ? item.getName() : itemDto.getName());
        item.setDescription(itemDto.getDescription()==null ? item.getDescription() : itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable()==null ? item.getAvailable() : itemDto.getAvailable());
        item.setOwner(user);


        return ItemMapper.toItemDto(itemRepository.save(item));

    }

    @Override
    public Boolean deleteAllItem() {
        itemRepository.deleteAll();
        return true;
    }

    @Override
    public Boolean deleteItem(Long id) {
        itemRepository.deleteById(id);
        return true;
    }

    public boolean checkUserValidDate(ItemDtoCreateRequest itemDtoCreateRequest) {
        if (itemDtoCreateRequest.getName() == null || itemDtoCreateRequest.getName().isEmpty()) {
            throw new ValidationException("Название должен быть указан");
        }
        if (itemDtoCreateRequest.getDescription() == null || itemDtoCreateRequest.getDescription().isEmpty()) {
            throw new ValidationException("Описание должен быть указан");
        }
        if (itemDtoCreateRequest.getAvailable() == null) {
            throw new ValidationException("Доступность должена быть указана");
        }
        return true;
    }
}