package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreRepository extends AbstractRepository<Genre> {
    List<Genre> getAll();

    Genre getData(Long id);

    Genre create(Genre data);

    Genre update(Genre data);
}
