package ru.practicum.shareit.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoWhiteValidator implements ConstraintValidator<NoWhitespace, String> {

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !string.contains(" ");
    }
}
