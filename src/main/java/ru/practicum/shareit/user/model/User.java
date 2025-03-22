package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.validation.NoWhitespace;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    @NotBlank(message = "Имя пусто?")
    @NoWhitespace
    private String name;
    @NotBlank
    @Email(message = "адрес не корректный")
    private String email;
}
