package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Получен запрос POST /users.");
        log.info("Попытка добавить пользователя {}.", user);
        return userService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Получен запрос PUT /users.");
        log.info("Попытка обновить пользователя {}.", user);
        return userService.updateUser(user);
    }

    @GetMapping
    public List<User> getAllUser() {
        log.info("Получен запрос GET /users.");
        log.info("Попытка показать всех юзеров ");
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        log.info("Получен запрос GET /users/{id}.");
        log.info("Попытка посмотреть пользователя по id ", id);
        return userService.getUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос PUT /users/{id}/friends/{friendId}.");
        log.info("Попытка добавить друга по id ", friendId, "для пользователя по id ", id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос DELETE /users/{id}/friends/{friendId}.");
        log.info("Попытка удалить друга по id ", friendId, "для пользователя по id ", id);
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id) {
        log.info("Получен запрос GET /users/{id}/friends.");
        log.info("Попытка показать всех друзей по id юзера ", id);
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получен запрос GET /users/{id}/friends/common/{otherId}.");
        log.info("Попытка показать общих друзей юзера по id ", id, "и юзера по id ", otherId);
        return userService.getCommonFriends(id, otherId);
    }
}