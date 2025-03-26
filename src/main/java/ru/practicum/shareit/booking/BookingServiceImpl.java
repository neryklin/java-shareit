package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingMapper;
import ru.practicum.shareit.common.StateBooking;
import ru.practicum.shareit.common.StatusBooking;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Класс реализации запросов к информации о пользователях
 */
@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public BookingDto create(Long userId, BookingDtoCreateRequest bookingDtoCreateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found " + userId));
        Item item = itemRepository.findById(bookingDtoCreateRequest.getItemId())
                .orElseThrow(() -> new NotFoundException("Item not found " + bookingDtoCreateRequest.getItemId()));
        if (!item.getAvailable()) {
            throw new ValidationException("Item не доступен для букинга");
        }
        Booking booking = BookingMapper.toBooking(bookingDtoCreateRequest);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(StatusBooking.WAITING);
        Booking booking2 = bookingRepository.save(booking);
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public BookingDto setApproval(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found " + bookingId));
        if (booking.getItem().getOwner().getId() != userId) {
            throw new ForbiddenException("ошибка доступа к Item не доступен для букинга");
        }
        StatusBooking statusBooking = approved ? StatusBooking.APPROVED : StatusBooking.REJECTED;
        booking.setStatus(statusBooking);
        return BookingMapper.toBookingDto(booking);
    }

    @Override
    public Optional<BookingDto> getBookingById(Long userId, Long bookingId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found " + userId));
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking not found " + bookingId));
        if (booking.getItem().getOwner().getId() != userId && booking.getBooker().getId() != userId) {
            throw new ForbiddenException("ошибка доступа к Item не доступен для букинга");
        }
        return Optional.of(BookingMapper.toBookingDto(booking));
    }

    @Override
    public List<BookingDto> getAllByOwnerAndState(Long ownerId, StateBooking state) {

        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("User not found with id = " + ownerId));
        Collection<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByItemOwnerIdOrderByStartDesc(ownerId);
            case PAST -> bookingRepository.findByItemOwnerIdAndEndBeforeOrderByStartDesc(ownerId, LocalDateTime.now());
            case FUTURE ->
                    bookingRepository.findByItemOwnerIdAndStartAfterOrderByStartDesc(ownerId, LocalDateTime.now());
            case CURRENT ->
                    bookingRepository.findByItemOwnerIdAndStartAfterAndEndBeforeOrderByStartDesc(ownerId, LocalDateTime.now(), LocalDateTime.now());
            case WAITING -> bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(ownerId, StateBooking.WAITING);
            case REJECTED ->
                    bookingRepository.findByItemOwnerIdAndStatusOrderByStartDesc(ownerId, StateBooking.REJECTED);
        };
        return bookings.stream().map(BookingMapper::toBookingDto).toList();
    }

    @Override
    public Collection<BookingDto> getAllByBookerAndState(long bookerId, StateBooking state) {
        User user = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("User not found " + bookerId));

        Collection<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByBookerIdOrderByStartDesc(bookerId);
            case PAST -> bookingRepository.findByBookerIdAndEndBeforeOrderByStartDesc(bookerId, LocalDateTime.now());
            case FUTURE -> bookingRepository.findByBookerIdAndStartAfterOrderByStartDesc(bookerId, LocalDateTime.now());
            case CURRENT ->
                    bookingRepository.findByBookerIdAndStartAfterAndEndBeforeOrderByStartDesc(bookerId, LocalDateTime.now(), LocalDateTime.now());
            case WAITING -> bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, StateBooking.WAITING);
            case REJECTED -> bookingRepository.findByBookerIdAndStatusOrderByStartDesc(bookerId, StateBooking.REJECTED);
        };
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}