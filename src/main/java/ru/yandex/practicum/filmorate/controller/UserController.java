package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Users;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, Users> userMap = new HashMap<>();
    private int userIdCounter = 0;

    private void validateUserFields(Users user) {
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

    private Users replaceVoidNameByLogin(Users users) {
        if (users.getName().isEmpty() && users.getName() == null) {
            users.setName(users.getLogin());
        }
        return users;
    }

    @GetMapping
    public Collection<Users> getUser() {
        log.info("Гет всех юзеров");
        return userMap.values();
    }

    @PostMapping
    public Users createUser(@RequestBody Users user) {
        log.debug("Получен запрос POST /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        validateUserFields(user);

        if (user.getId() == null || user.getId() == 0) {
            userIdCounter++;
            user.setId(userIdCounter);
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        userMap.put(user.getId(), user);

        log.info("Фильм добавлен");

        return user;
    }

    @PutMapping
    public Users updateUser(@Valid @RequestBody Users user) {
        log.debug("Получен запрос PUT /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        validateUserFields(user);

        Users userReplaced = replaceVoidNameByLogin(user);

            for (Map.Entry<Integer, Users> entry : userMap.entrySet()) {
                if (entry.getValue().getId() == user.getId()) {
                    if (user.getId() == null || user.getId() == 0) {
                        userIdCounter++;
                        user.setId(userIdCounter);
                    }
                    userMap.put(user.getId(), user);
                    userMap.replace(entry.getKey(), user);
                    log.info("Фильм добавлен");
                } else {
                    throw new ValidationException(HttpStatus.NOT_FOUND,
                            "Такого id нет в User");
                }
            }
        return user;
    }
}