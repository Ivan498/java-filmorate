package ru.yandex.practicum.filmorate.dao.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserRepository;

@Component
@Slf4j
public class InMemoryUserRepository extends InMemoryBaseRepository<User> implements UserRepository {
    public InMemoryUserRepository() {
    }

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("No user name: was set login '{}'.", user.getLogin());
        }
    }

    @Override
    public boolean isExists(Long id) {
        throw new UnsupportedOperationException();
    }

}
