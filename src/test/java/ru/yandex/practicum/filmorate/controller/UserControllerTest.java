package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName(value = "Запрос на проверку пройден")
    public void createUserTestValid() {
        User user = User.builder()
                .id(1L)
                .email("mail@yandex.ru")
                .login("doloreUpdate")
                .name("est adipisicing")
                .birthday(LocalDate.of(2004, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.OK, userMap.getStatusCode());
    }

    @Test
    @DisplayName(value = "Запрос на проверку не пройден")
    public void createUserTestInValid() {
        User user = User.builder()
                .id(1L)
                .email("vankar28@")
                .login("Karavaev")
                .name("vanya28")
                .birthday(LocalDate.of(2200, 1, 20))
                .build();
        ResponseEntity<User> userMap = testRestTemplate.postForEntity("/users", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, userMap.getStatusCode());
    }
}
