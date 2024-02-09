package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FilmController {
    private int idCount = 0;
    private final Map<Integer, Film> filmMap = new HashMap<>();

    LocalDate earliestAllowedDate = LocalDate.of(1895, 12, 28);

    @GetMapping
    public Collection<Film> getFilm() {
        return filmMap.values();
    }

    @PostMapping
    public ResponseEntity<?> createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isAfter(earliestAllowedDate)) {
            if (film.getId() == null || film.getId() == 0) {
                idCount++;
                int id = idCount;
                film.setId(id);
            }
            filmMap.put(film.getId(), film);
            log.info("Фильм добавлен");
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
                    if (film.getId() == null || film.getId() == 0) {
                        idCount++;
                        int id = idCount;
                        film.setId(id);
                    }
                    filmMap.put(film.getId(), film);
                    filmMap.replace(entry.getKey(), film);
                    log.info("Фильм обновлен");
                    return new ResponseEntity<>(film, HttpStatus.OK);

                }
            }
        }
        return new ResponseEntity<>("Ошибка", HttpStatus.BAD_REQUEST);
    }
}