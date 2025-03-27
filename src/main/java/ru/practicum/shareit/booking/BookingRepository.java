package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.common.StateBooking;
import ru.practicum.shareit.common.StatusBooking;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;



public interface BookingRepository extends JpaRepository<Booking, Long> {


    Optional<Booking> findById(Long id);

    List<Booking> findByItemOwnerIdAndStatusOrderByStartDesc(long ownerId, StateBooking status);

    List<Booking> findByBookerIdOrderByStartDesc(long bookerId);

    List<Booking> findByBookerIdAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime before);

    List<Booking> findByBookerIdAndStartAfterAndEndBeforeOrderByStartDesc(long bookerId, LocalDateTime after, LocalDateTime before);

    List<Booking> findByBookerIdAndStartAfterOrderByStartDesc(long bookerId, LocalDateTime after);

    List<Booking> findByBookerIdAndStatusOrderByStartDesc(long bookerId, StateBooking status);

    Collection<Booking> findAllByBookerIdAndItemIdAndEndBefore(long bookerId, long itemId, LocalDateTime end);

    List<Booking> findByItemOwnerIdOrderByStartDesc(long ownerId);

    List<Booking> findByItemOwnerIdAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime before);

    List<Booking> findByItemOwnerIdAndStartAfterAndEndBeforeOrderByStartDesc(long ownerId, LocalDateTime after, LocalDateTime before);

    List<Booking> findByItemOwnerIdAndStartAfterOrderByStartDesc(long ownerId, LocalDateTime after);

    Optional<Booking> findTopByItemIdAndStartBeforeAndStatusOrderByEndDesc(Long itemId, LocalDateTime now, StatusBooking status);

    Optional<Booking> findTopByItemIdAndStartAfterAndStatusOrderByStartAsc(Long itemId, LocalDateTime now, StatusBooking status);


}
