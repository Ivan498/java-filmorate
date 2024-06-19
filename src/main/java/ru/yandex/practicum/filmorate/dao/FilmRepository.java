package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmRepository extends AbstractRepository<Film> {
    Film create(Film data);

    Film update(Film data);

    List<Film> getAll();

    Film getData(Long id);
}