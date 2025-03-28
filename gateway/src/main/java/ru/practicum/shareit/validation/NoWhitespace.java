package ru.practicum.shareit.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoWhiteValidator.class)
public @interface NoWhitespace {
    String message() default "{NoWhitespace.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
