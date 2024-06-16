package ru.yandex.practicum.filmorate.dao.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dao.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JdbcUserRepository implements UserRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        replaceVoidNameByLogin(user);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday());

        user.setId(simpleJdbcInsert.executeAndReturnKey(params).longValue());
        return user;
    }

    @Override
    public User update(User user) {
        replaceVoidNameByLogin(user);
        long id = user.getId();

        String sql = "update users set email = ?, login = ?, name = ?, birthday = ? where id = ?";
        int rowsUpdated = jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                id);
        if (rowsUpdated <= 0) {
            throw new NotFoundException(String.format("User with id = %s not found", id));
        }
        return getData(id);
    }

    @Override
    public List<User> getAll() {
        String sqlQuery = "select * from users";
        return jdbcTemplate.query(sqlQuery, JdbcUserRepository::createUser);
    }

    @Override
    public User getData(Long id) {
        String sqlQuery = "select * from users where id = ?";
        List<User> users = jdbcTemplate.query(sqlQuery, JdbcUserRepository::createUser, id);
        if (users.size() != 1) {
            throw new NotFoundException(String.format("User with id = %s is not single", id));
        }
        return users.get(0);
    }

    static User createUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .name(rs.getString("name"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }

    private void replaceVoidNameByLogin(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.info("No user name: was set login '{}'.", user.getLogin());
        }
    }

    @Override
    public boolean isExists(Long id) {
        boolean isExist = false;

        String sql = "select id from users where id = ?";
        List<Long> getIdList = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong("id"), id);

        if (!getIdList.isEmpty() && getIdList.get(0).equals(id)) {
            isExist = true;
        } else {
            throw new NotFoundException(String.format("User with id = %s not found", id));
        }
        return isExist;
    }
}
