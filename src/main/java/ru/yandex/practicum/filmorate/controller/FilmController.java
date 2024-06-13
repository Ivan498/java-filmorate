package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    FilmRepository filmRepository;
    FilmService filmService;

    @Autowired
    public FilmController(FilmRepository filmRepository, FilmService filmService) {
        this.filmService = filmService;
        this.filmRepository = filmRepository;
    }

    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    public void validateUserFields(Film film) {
        if (film.getReleaseDate().isBefore(earliestAllowedDate)) {
            log.debug("Не пройдена валидация email: {}", film.getReleaseDate());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр ReleaseDate не должна быть менбше даты 1895.12.28");
        }
        if (film.getName() == null || film.getName().isBlank()) {
            log.debug("Не пройдена валидация name: {}", film.getName());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр name не должен быть пустым");
        }

        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.debug("Не пройдена валидация description: {}", film.getDescription());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр description должен содержать не более 200 символов");
        }

        if (film.getDuration() == null || film.getDuration() <= 0) {
            log.debug("Не пройдена валидация duration: {}", film.getDuration());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр duration должен быть положительным числом");
        }
    }

    @GetMapping
    public Collection<Film> getFilm() {
        log.info("Гет всех фильмов");
        return filmService.getFilm();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос POST /films.");
        log.info("Попытка добавить фильм {}.", film);

        validateUserFields(film);

        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films.");
        log.info("Попытка добавить фильм {}.", film);

        validateUserFields(film);

        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Optional<Film> getFilmById(@PathVariable Integer id) {
        log.info("Получен запрос GET /films/{id}.");
        log.info("Попытка посмотреть фильм по id ", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addFilmPutsLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        log.info("Попытка добавить лайка на фильм по id ", id);
        return filmService.addFilmPutsLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable Integer id, @PathVariable Integer userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        log.info("Попытка удалить лайка на фильм по id ", id);
        filmService.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularListFilms(@RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        log.info("Получен запрос GET /films/popular?count={count}.");
        log.info("Попытка посмотреть популярные фильмы в кол-ве ", count);
        return filmService.getPopularFilms(count);
    }
}
