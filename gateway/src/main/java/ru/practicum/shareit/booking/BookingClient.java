package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.booking.dto.BookingDtoCreateRequest;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.common.StateBooking;

import java.util.Map;

@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> getAllByBookerAndState(long bookerId, StateBooking stateBooking) {
        Map<String, Object> parameters = Map.of("state", stateBooking.name());
        return get("?state={state}", bookerId, parameters);
    }

    public ResponseEntity<Object> getAllByOwnerAndState(Long userId, StateBooking stateBooking) {
        Map<String, Object> parameters = Map.of("state", stateBooking.name());
        return get("/owner?state={state}", userId, parameters);
    }

    public ResponseEntity<Object> getBookingById(Long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> setApproval(Long userId, Long bookingId, @NotNull Boolean approved) {
        Map<String, Object> parameters = Map.of("approved", approved);
        return patch("/" + bookingId + "?approved={approved}", userId, parameters, null);
    }

    public ResponseEntity<Object> create(Long userId, @Valid BookingDtoCreateRequest bookingDtoCreateRequest) {
        return post("", userId, bookingDtoCreateRequest);
    }
}

