package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

public interface GenresRepository {
    void updateGenresForFilm(Film film, Set<Genre> genres);

    Set<Genre> getGenresForFilm(Long filmId);
}
