package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;
    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

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
        return userService.getUser();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        log.debug("Получен запрос POST /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        validateUserFields(user);

        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.debug("Получен запрос PUT /users.");
        log.debug("Попытка добавить пользователя {}.", user);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        validateUserFields(user);

        return userService.updateUser(user);
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        log.debug("Получен запрос GET /users/{id}.");
        log.debug("Попытка посмотреть пользователя по id ", id);
        return userService.findByIdUser(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.debug("Получен запрос PUT /users/{id}/friends/{friendId}.");
        log.debug("Попытка добавить друга по id ", friendId, "для пользователя по id ", id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.debug("Получен запрос DELETE /users/{id}/friends/{friendId}.");
        log.debug("Попытка удалить друга по id ", friendId, "для пользователя по id ", id);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable Integer id) {
        log.debug("Получен запрос GET /users/{id}/friends.");
        log.debug("Попытка показать всех друзей по id юзера ", id);
        return userService.getAllFriendsByUserId(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getAllCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.debug("Получен запрос GET /users/{id}/friends/common/{otherId}.");
        log.debug("Попытка показать общих друзей юзера по id ", id, "и юзера по id ", otherId);
        return userService.getAllCommonFriends(id, otherId);
    }
}