package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    FilmStorage filmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmService = filmService;
        this.filmStorage = filmStorage;
    }

    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    public void validateUserFields(Film film) {
        if (film.getReleaseDate().isBefore(earliestAllowedDate)) {
            log.debug("Не пройдена валидация email: {}", film.getReleaseDate());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр ReleaseDate не должна быть менбше даты 1895.12.28");
        }
    }

    @GetMapping
    public Collection<Film> getFilm() {
        log.info("Гет всех фильмов");
        return filmStorage.getFilm();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST /films.");
        log.info("Попытка добавить пользователя {}.", film);

        validateUserFields(film);

        return filmStorage.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films.");
        log.info("Попытка добавить пользователя {}.", film);

        validateUserFields(film);

        return filmStorage.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("Получен запрос GET /films/{id}.");
        return filmStorage.findByIdFilm(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addFilmPutsLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        return filmService.addFilmPutsLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable Integer id, @PathVariable Long userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        filmService.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularListFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        log.info("Получен запрос GET /films/popular?count={count}.");
        return filmService.getPopularFilms(count);
    }
}
