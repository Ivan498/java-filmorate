package ru.yandex.practicum.filmorate.exception;

public class MethodValidationException extends RuntimeException {
    public MethodValidationException(String reason) {
        super(reason);
    }
}