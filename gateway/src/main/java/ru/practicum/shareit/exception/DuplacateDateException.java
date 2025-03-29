package ru.practicum.shareit.exception;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplacateDateException extends RuntimeException {
    public DuplacateDateException(String message) {
        super(message);
        log.error(message);
    }
}
