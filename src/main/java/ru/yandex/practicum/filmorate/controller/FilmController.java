package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Гет всех фильмов");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Получен запрос PUT /films.");
        log.info("Попытка добавить фильм {}.", film);
        return filmService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getAllFilm() {
        log.info("Получен запрос GET /films.");
        log.info("Попытка показать фильм.");
        return filmService.getAllFilm();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        log.info("Получен запрос GET /films/{id}.");
        log.info("Попытка посмотреть фильм по id ", id);
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        log.info("Попытка добавить лайк");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        log.info("Попытка удалить лайк");
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Получен запрос GET /popular.");
        log.info("Попытка получить популярные фильмы");
        return filmService.getPopularFilm(count);
    }
}