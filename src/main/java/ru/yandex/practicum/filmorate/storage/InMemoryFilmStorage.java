package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RestControllerAdvice
public class InMemoryFilmStorage implements FilmStorage {
    private int idCount = 0;
    private final Map<Integer, Film> filmMap = new HashMap<>();

    @Override
    public Film findByIdFilm(Integer id) {
        Film film = filmMap.get(id);
        if (film == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }
        return film;
    }

    @Override
    public void saveFilm(Film film) {
        filmMap.put(film.getId(), film);
    }

    @Override
    public Collection<Film> getFilm() {
        return filmMap.values();
    }

    @Override
    public Film createFilm(Film film) {
        if (film.getId() == null || film.getId() == 0) {
            idCount++;
            film.setId(idCount);
        }

        filmMap.put(film.getId(), film);
        log.info("Фильм добавлен");

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmMap.containsKey(film.getId())) {
            filmMap.put(film.getId(), film);
            log.info("Film обновлен");
        } else {
            throw new ValidationException(HttpStatus.NOT_FOUND, "Такого id нет в фильмах");
        }

        return film;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public NotFoundException handleNotFoundException(NotFoundException ex) {
        return new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
    }

}
