package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName(value = "Запрос на проверку пройден")
    public void createFilmTestValid() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден")
    public void createFilmTestInValid() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, filmMap.getStatusCode());
    }
}
