package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RestControllerAdvice
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> userMap = new HashMap<>();
    private Long userIdCounter = 0L;

    @Override
    public User findByIdUser(Long id) {
        User user = userMap.get(id);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
        return user;
    }

    public void saveUser(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public Collection<User> getUser() {
        return userMap.values();
    }

    @Override
    public User createUser(User user) {
        if (user.getId() == null || user.getId() == 0) {
            userIdCounter++;
            user.setId(userIdCounter);
        }

        userMap.put(user.getId(), user);

        log.info("Фильм добавлен");

        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userMap.containsKey(user.getId())) {
            if (user.getId() == null || user.getId() == 0) {
                userIdCounter++;
                user.setId(userIdCounter);
            }
            userMap.replace(user.getId(), user);
            log.info("User обновлен");
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND,
                    "Такого id нет в User");
        }

        return user;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundException handleNotFoundException(NotFoundException ex) {
        return new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
    }

}
