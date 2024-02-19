package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;

public interface UserStorage {
    User findByIdUser(Long id);

    void saveUser(User user);

    Collection<User> getUser();

    User createUser(@RequestBody User user);

    User updateUser(@Valid @RequestBody User user);
}
