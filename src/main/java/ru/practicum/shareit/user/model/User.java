package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.validation.NoWhitespace;

/**
 * TODO Sprint add-controllers.
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    @NotBlank(message = "Имя пусто?")
    @NoWhitespace
    private String name;
    @Email(message = "адрес не корректный")
    private String email;
}
