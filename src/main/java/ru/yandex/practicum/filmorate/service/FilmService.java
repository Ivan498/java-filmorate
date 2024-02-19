package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

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
            return film;
        }

        return null;
    }

    public void deleteFilmLike(Integer id, Long userId) {
        User user = userStorage.findByIdUser(userId);
        Film film = filmStorage.findByIdFilm(id);

        if (user != null && film != null) {
            Set<Long> likes = film.getLike();
            if (likes == null) {
                return;
            }
            likes.remove(user.getId());
            film.setLike(likes);

            filmStorage.saveFilm(film);
        }
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> filmCollection = filmStorage.getFilm();

        List<Film> popularFilms = filmCollection.stream()
                .filter(film -> (film).getLike() != null)
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLike().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
        return popularFilms;
    }
}