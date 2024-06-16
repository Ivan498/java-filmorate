package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.GenreRepository;

import java.util.List;

@Service
public class GenreService {
    GenreRepository genreStorage;

    @Autowired
    public GenreService(GenreRepository genreStorage) {
        this.genreStorage = genreStorage;
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public Genre getGenre(Long id) {
        return genreStorage.getData(id);
    }
}
