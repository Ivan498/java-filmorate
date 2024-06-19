package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.GenresRepository;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.dao.LikeRepository;
import ru.yandex.practicum.filmorate.dao.UserRepository;

@Slf4j
@Service
public class FilmService {
    FilmRepository filmStorage;
    GenresRepository genresRepository;
    LikeRepository likeRepository;
    UserRepository userStorage;

    @Autowired
    public FilmService(@Qualifier("jdbcFilmRepository") FilmRepository filmStorage,
                       GenresRepository genresRepository,
                       LikeRepository likeRepository,
                       @Qualifier("jdbcUserRepository") UserRepository userStorage) {
        this.filmStorage = filmStorage;
        this.genresRepository = genresRepository;
        this.likeRepository = likeRepository;
        this.userStorage = userStorage;
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.isExists(userId);
        log.info("Добавлен лайк на фильм с id " + filmId);
        likeRepository.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        userStorage.isExists(userId);
        log.info("Удален лайк на фильм с id " + filmId);
        likeRepository.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilm(Integer count) {
        return likeRepository.getPopular(count);
    }

    public Film createFilm(Film value) {
        Film film = filmStorage.create(value);
        if (value.getGenres().size() != 0) {
            genresRepository.updateGenresForFilm(film, value.getGenres());
            film.setGenres(genresRepository.getGenresForFilm(film.getId()));
        }
        return film;
    }

    public Film updateFilm(Film value) {
        Film film = filmStorage.update(value);

        Set<Genre> genres = value.getGenres();
        int genresSetSize = genres.size();

        genresRepository.updateGenresForFilm(film, genres);
        if (genresSetSize != 0) {
            film.setGenres(genresRepository.getGenresForFilm(film.getId()));
        } else if (genresSetSize == 0) {
            film.setGenres(new LinkedHashSet<>());
        }
        return film;
    }

    public List<Film> getAllFilm() {
        List<Film> films = filmStorage.getAll();
        List<Film> filmsWithGenres = new ArrayList<>();
        for (Film film : films) {
            Long id = film.getId();
            film.setGenres(genresRepository.getGenresForFilm(id));
            filmsWithGenres.add(film);
        }
        return filmsWithGenres;
    }

    public Film getFilmById(Long id) {
        Film film = filmStorage.getData(id);
        film.setGenres(genresRepository.getGenresForFilm(id));
        return film;
    }
}
