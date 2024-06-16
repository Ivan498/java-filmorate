package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private GenreService genreService;
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @GetMapping
    public List<Genre> getAll() {
        log.info("Get all genres.");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable Long id) {
        log.info("get genre by id");
        return genreService.getGenre(id);
    }
}