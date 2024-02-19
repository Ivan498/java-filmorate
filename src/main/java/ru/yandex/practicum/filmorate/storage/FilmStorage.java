package ru.yandex.practicum.filmorate.storage;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;

public interface FilmStorage {
    Film findByIdFilm(Integer id);

    void saveFilm(Film film);

    Collection<Film> getFilm();

    Film createFilm(@Valid @RequestBody Film film);

    Film updateFilm(@Valid @RequestBody Film film);
}
