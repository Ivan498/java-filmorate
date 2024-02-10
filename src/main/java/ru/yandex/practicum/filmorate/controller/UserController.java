package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> userMap = new HashMap<>();
    private int userIdCounter = 0;

    private void validateUserFields(User user) {
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate birthday = user.getBirthday();

        if (email == null || email.isBlank() || !email.contains("@")) {
            log.debug("Не пройдена валидация email: {}", email);

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр email не должен быть пустым и должен содержать символ @");
        }

        if (login == null || login.isBlank() || !login.replaceAll("\\s", "").equals(login)) {
            log.debug("Не пройдена валидация login: {}", login);

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр login не должен быть пустым и содержать пробелы");
        }

        if (birthday != null && birthday.isAfter(LocalDate.now())) {
            log.debug("Не пройдена валидация birthday: {}", birthday);

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр birthday не должен быть в будущем");
        }
    }

    private User replaceVoidNameByLogin(User user) {
        if (user.getName().isEmpty() && user.getName() == null) {
            user.setName(user.getLogin());
        }
        return user;
    }

    @GetMapping
    public Collection<User> getUser() {
        log.info("Гет всех юзеров");
        return userMap.values();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.debug("Получен запрос POST /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        validateUserFields(user);

        if (user.getId() == null || user.getId() == 0) {
            userIdCounter++;
            user.setId(userIdCounter);
        }

        userMap.put(user.getId(), user);

        log.info("Фильм добавлен");

        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос PUT /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        validateUserFields(user);

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

    }