package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UserRepository extends AbstractRepository<User> {
    User create(User data);

    User update(User data);

    List<User> getAll();

    User getData(Long id);

    boolean isExists(Long id);
}
