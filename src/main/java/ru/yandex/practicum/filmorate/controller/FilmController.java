package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private int idCount = 0;
    private final Map<Integer, Film> filmMap = new HashMap<>();

    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    public void validateUserFields(Film film) {
        if (!film.getReleaseDate().isAfter(earliestAllowedDate)) {
            log.debug("Не пройдена валидация email: {}", film.getReleaseDate());

            throw new ValidationException(HttpStatus.BAD_REQUEST,
                    "Параметр ReleaseDate не должна быть менбше даты 1895.12.28");
        }
    }

    @GetMapping
    public Collection<Film> getFilm() {
        log.info("Гет всех фильмов");
        return filmMap.values();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        validateUserFields(film);

        if (film.getId() == null || film.getId() == 0) {
            idCount++;
            film.setId(idCount);
        }

        filmMap.put(film.getId(), film);
        log.info("Фильм добавлен");

        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.debug("Получен запрос PUT /users.");
        log.debug("Попытка добавить пользователя {}.", film);

        validateUserFields(film);

            for (Map.Entry<Integer, Film> entry : filmMap.entrySet()) {
                if (entry.getValue().getId() == film.getId()) {
                    filmMap.put(film.getId(), film);
                    filmMap.replace(entry.getKey(), film);
                    log.info("Фильм обновлен");
                } else {
                    throw new ValidationException(HttpStatus.NOT_FOUND,
                            "Такого id нет в фильмах");
                }
            }
            return film;
        }
    }
