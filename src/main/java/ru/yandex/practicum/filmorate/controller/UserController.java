package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Users;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, Users> userMap = new HashMap<>();
    private int idCount = 0;
    LocalDate localDate;

    @GetMapping
    public Collection<Users> getUser() {
        return userMap.values();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody Users users) {
        if (users.getName() == null || users.getName().isEmpty()) {
            users.setName(users.getLogin());
        }

        if (users.getEmail().contains("@") && users.getBirthday().isBefore(localDate.now())) {
            if (users.getId() == null || users.getId() == 0) {
                userMap.put(users.getId(), users);
                idCount++;
                int id = idCount;
                users.setId(id);
            }
            userMap.put(users.getId(), users);
            log.info("Фильм добавлен");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Ошибка в значениях", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody Users users) {
        if (users.getName() == null || users.getName().isEmpty()) {
            users.setName(users.getLogin());
        }
        if (users.getEmail().contains("@") && users.getBirthday().isBefore(localDate.now())) {
            for (Map.Entry<Integer, Users> entry : userMap.entrySet()) {
                if (entry.getValue().getId() == users.getId()) {
                    if (users.getId() == null || users.getId() == 0) {
                        idCount++;
                        int id = idCount;
                        users.setId(id);
                    }
                    userMap.put(users.getId(), users);
                    userMap.replace(entry.getKey(), users);
                    log.info("Фильм добавлен");
                    return new ResponseEntity<>(users, HttpStatus.OK);
                }
            }
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Ошибка в значениях", HttpStatus.BAD_REQUEST);
        }
    }
}