package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.BaseUnit;

import java.util.List;

public interface AbstractRepository<T extends BaseUnit> {
    T create(T data);

    T update(T data);

    List<T> getAll();

    T getData(Long id);
}
