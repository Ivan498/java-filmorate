package ru.yandex.practicum.filmorate.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> filmMap = new HashMap<>();

    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> getFilm() {
        return filmMap.values();
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isAfter(earliestAllowedDate)) {
            filmMap.put(film.getId(), film);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ошибка", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isAfter(earliestAllowedDate)) {
            for (Map.Entry<Integer, Film> entry : filmMap.entrySet()) {
                if (entry.getValue().getId() == film.getId()) {
                    filmMap.replace(entry.getKey(), film);
                    return new ResponseEntity<>(film, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Ошибка", HttpStatus.BAD_REQUEST);
    }
}