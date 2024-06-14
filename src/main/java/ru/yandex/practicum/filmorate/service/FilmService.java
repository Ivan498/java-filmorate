package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmRepository;
import ru.yandex.practicum.filmorate.storage.UserRepository;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;
    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    @Autowired
    public FilmService(FilmRepository filmRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }

    public void validateUserFields(Film film) {
        if (film.getReleaseDate().isBefore(earliestAllowedDate)) {
            log.debug("Не пройдена валидация email: {}", film.getReleaseDate());

            throw new ValidationException(
                    "Параметр ReleaseDate не должна быть менбше даты 1895.12.28");
        }
    }

    public Collection<Film> getFilm() {
        return filmRepository.findAll();
    }

    public Film createFilm(Film film) {
        validateUserFields(film);
        return filmRepository.save(film);
    }

    public Film updateFilm(Film film) {
        validateUserFields(film);
        List<Film> filmList = filmRepository.findAll();
        List<Integer> filmIdList = filmList.stream().map(Film::getId).collect(Collectors.toList());
        if (!filmIdList.contains(film.getId())) {
            throw new NotFoundException("Film not found");
        }
        filmRepository.save(film);
        return film;
    }

    public Optional<Film> getFilmById(Integer id) {
        return filmRepository.findById(id);
    }

    public Film addFilmPutsLike(Integer filmId, Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Film> filmOptional = filmRepository.findById(filmId);

        if (userOptional.isPresent() && filmOptional.isPresent()) {
            User user = userOptional.get();
            Film film = filmOptional.get();

            Set<User> likes = film.getLikes();
            if (likes == null) {
                likes = new HashSet<>();
            }
            likes.add(user);
            film.setLikes(likes);

            filmRepository.save(film);
            log.info("Добавлен лайк на фильм с id " + filmId);
            return film;
        }

        throw new NotFoundException("Фильм или пользователь не найден");
    }

    public void deleteFilmLike(Integer id, Integer userId) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent() && filmOptional.isPresent()) {
            User user = userOptional.get();
            Film film = filmOptional.get();
            Set<User> likes = film.getLikes();
            if (likes == null) {
                likes = new HashSet<>();
            }
            likes.remove(user);
            log.info("Удален лайк на фильм с id " + id);
        }
        else {
            throw new NotFoundException("Фильм или пользователь не найден");
        }
    }

    public Collection<Film> getPopularFilms(Integer count) {
        Collection<Film> filmCollection = filmRepository.findAll();

        if (filmCollection == null) {
            throw new NotFoundException("Not found");
        }

        List<Film> popularFilms = filmCollection.stream()
                .filter(film -> film.getLikes() != null)
                .sorted(Comparator.comparingInt(film -> ((Film) film).getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
        log.info("Показали популярные фильмы");
        return popularFilms;
    }

}

