package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmorateApplicationTests {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName(value = "Запрос на не проверку пройден из-за даты")
    public void testUserDateInvalidRequest() {
        User user = User.builder()
                .id(1L)
                .email("mail@yandex.ru")
                .login("doloreUpdate")
                .name("est adipisicing")
                .birthday(LocalDate.of(9999, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но поменялось name на login")
    public void testUserNameChangedLoginValidRequest() {
        User user = User.builder()
                .id(1L)
                .email("mail@yandex.ru")
                .login("doloreUpdate")
                .name("")
                .birthday(LocalDate.of(2000, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        User userBody = userMap.getBody();
        assertEquals(userBody.getName(), "doloreUpdate");
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но поменялось name на login")
    public void testUserMainChangedValidRequest() {
        User user = User.builder()
                .id(1L)
                .email("mailyandex.ru")
                .login("doloreUpdate")
                .name("Karavaev")
                .birthday(LocalDate.of(2000, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но мы вводим пользователя без id")
    public void testUserWithoutIdRequest() {
        User user = User.builder()
                .email("mail@yandex.ru")
                .login("doloreUpdate")
                .name("Karavaev")
                .birthday(LocalDate.of(2000, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден из-за даты")
    public void testFilmDateInvalidRequest() {
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

    @Test
    @DisplayName(value = "Запрос на проверку не пройден из-за продолжительности фильма")
    public void testFilmDurationInvalidRequest() {
        Film film = Film.builder()
                .id(1)
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(0)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.BAD_REQUEST, filmMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку пройден, но мы вводим пользователя без id")
    public void testFilmWithoutIdRequest() {
        Film film = Film.builder()
                .name("Фильм")
                .description("Описание")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(120)
                .build();
        ResponseEntity<Film> filmMap = testRestTemplate.postForEntity("/films", film, Film.class);
        assertEquals(HttpStatus.OK, filmMap.getStatusCode());
    }
}
