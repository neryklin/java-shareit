package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private long id;
    @NotBlank(message = "Название пусто?")
    @Size(max = 50)
    private String name;
    @NotBlank(message = "Описание пусто?")
    @Size(max = 200)
    private String description;
    private Boolean available;
    @NotNull
    private User owner;
    private ItemRequest request;


}
