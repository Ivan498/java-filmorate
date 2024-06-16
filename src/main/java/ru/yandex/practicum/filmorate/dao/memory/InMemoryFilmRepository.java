package ru.yandex.practicum.filmorate.dao.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.FilmRepository;

import java.time.LocalDate;

@Component
@Slf4j
public class InMemoryFilmRepository extends InMemoryBaseRepository<Film> implements FilmRepository {
    public static final LocalDate START_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public InMemoryFilmRepository() {
    }

    @Override
    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Film release date is invalid: {}.", film.getReleaseDate());
            throw new ValidationException("Film release date is invalid.");
        }
    }

}