package ru.practicum.shareit.item;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.common.StatusBooking;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreateRequest;
import ru.practicum.shareit.item.dto.ItemExtendDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.CommentMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс реализации запросов к информации о пользователях
 */
@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRequestRepository itemRequestRepository;


    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> findAllItem() {
        return itemRepository.findAll().stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ItemDto> getItemByUserId(Long userId) {
        return itemRepository.findAllByOwnerId(userId).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found " + userId));
        if (checkUserValidDate(itemDtoCreateRequest)) {
            Item item = ItemMapper.toItem(itemDtoCreateRequest);
            item.setOwner(user);
            if (itemDtoCreateRequest.getRequestId() != null) {
                ItemRequest itemRequest = itemRequestRepository.findById(itemDtoCreateRequest.getRequestId())
                        .orElseThrow(() -> new NotFoundException("Запрос с id = " + itemDtoCreateRequest.getRequestId() + " не найден"));
                item.setRequest(itemRequest);
            }
            item = itemRepository.save(item);
            return ItemMapper.toItemDto(item);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public ItemExtendDto getItemById(Long userId, Long itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found " + itemId));

        Booking lastBooking = null;
        Booking nextBooking = null;

        if (item.getOwner().getId() == userId) {
            lastBooking = bookingRepository
                    .findTopByItemIdAndStartBeforeAndStatusOrderByEndDesc(itemId, LocalDateTime.now(), StatusBooking.APPROVED)
                    .orElse(null);
            nextBooking = bookingRepository
                    .findTopByItemIdAndStartAfterAndStatusOrderByStartAsc(itemId, LocalDateTime.now(), StatusBooking.APPROVED)
                    .orElse(null);
            ;
        }
        List<CommentDto> comments = commentRepository.findAllByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());

        return ItemMapper.toItemDto(item, lastBooking, nextBooking, comments);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found " + userId));
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found " + itemId));
        item.setName(itemDto.getName() == null ? item.getName() : itemDto.getName());
        item.setDescription(itemDto.getDescription() == null ? item.getDescription() : itemDto.getDescription());
        item.setAvailable(itemDto.getAvailable() == null ? item.getAvailable() : itemDto.getAvailable());
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

    @Override
    public CommentDto addCommentToItem(long authorId, long itemId, CommentDto commentDto) {
        Comment comment = CommentMapper.toComment(authorId, itemId, commentDto);
        Collection<Booking> authorBookings = bookingRepository.findAllByBookerIdAndItemIdAndEndBefore(authorId, itemId, LocalDateTime.now());

        if (authorBookings.isEmpty()) {
            throw new ValidationException(String.format("(ошибка доступа к comments не доступен для букинга",
                    comment.getAuthor().getId(), comment.getItem().getId()));
        }
        User user = userRepository.findById(comment.getAuthor().getId())
                .orElseThrow(() -> new NotFoundException("User not found " + comment.getAuthor().getId()));
        Item item = itemRepository.findById(comment.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Item not found " + comment.getItem().getId()));


        comment.setItem(item);
        comment.setAuthor(user);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
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