package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilmPutsLike(Integer id, Long userId) {
        User user = userStorage.findByIdUser(userId);
        Film film = filmStorage.findByIdFilm(id);

        if (user != null && film != null) {
            Set<Long> likes = film.getLike();
            if (likes == null) {
                likes = new HashSet<>();
            }
            likes.add(user.getId());
            film.setLike(likes);

            filmStorage.saveFilm(film);
            log.info("Добавили лайк на фильм по id " + id);
            return film;
        }

        throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
    }

    public void deleteFilmLike(Integer id, Long userId) {
        User user = userStorage.findByIdUser(userId);
        Film film = filmStorage.findByIdFilm(id);

        if (user == null || film == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }

        if (user != null && film != null) {
            Set<Long> likes = film.getLike();
            if (likes == null) {
                return;
            }
            likes.remove(user.getId());
            film.setLike(likes);
            log.info("Удалили лайк на фильм по id " + id);
            filmStorage.saveFilm(film);
        }
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> filmCollection = filmStorage.getFilm();

        if (filmCollection == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Not found");
        }

        List<Film> popularFilms = filmCollection.stream()
                .filter(film -> (film).getLike() != null)
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLike().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Показали популярные фильмы");
        return popularFilms;
    }
}
