package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.JpaGenreRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class GenreService {
    JpaGenreRepository jpaGenreRepository;

    public GenreService(JpaGenreRepository jpaGenreRepository) {
        this.jpaGenreRepository = jpaGenreRepository;
    }

    public Collection<Genre> getAllGenres() {
        return jpaGenreRepository.findAll();
    }

    public Genre getGenreById(Integer id) {
        Optional<Genre> optionalGenre = jpaGenreRepository.findById(id);
        if (optionalGenre.isPresent()) {
            return optionalGenre.get();
        } else {
            throw new NotFoundException("Mpa с ID " + id + " не найден");
        }
    }
}
