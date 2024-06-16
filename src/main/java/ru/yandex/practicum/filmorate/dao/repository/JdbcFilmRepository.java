package ru.yandex.practicum.filmorate.dao.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmRepository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static ru.yandex.practicum.filmorate.dao.memory.InMemoryFilmRepository.START_RELEASE_DATE;

@Component
@Slf4j
public class JdbcFilmRepository implements FilmRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcFilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        validate(film);
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getDataSource())
                .withTableName("films")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> params = Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "rate", film.getRate(),
                "duration", film.getDuration(),
                "mpa_id", film.getMpa().getId());

        film.setId(simpleJdbcInsert.executeAndReturnKey(params).longValue());
        return film;
    }

    @Override
    public Film update(Film film) {
        validate(film);
        long id = film.getId();
        String sql = "update films set name = ?, description = ?, release_date = ?, rate = ?, duration = ?, " +
                "mpa_id = ?  where id = ?";

        int rowsUpdated = jdbcTemplate.update(
                sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getRate(),
                film.getDuration(),
                film.getMpa().getId(),
                id);
        if (rowsUpdated <= 0) {
            throw new NotFoundException(String.format("Film with id = %s not found", id));
        }
        return getData(id);
    }

    @Override
    public List<Film> getAll() {
        String sql = "select f.id as id, f.name, f.description, f.release_date, f.rate, f.duration, " +
                "mpa.id as mpa_id, mpa.name as mpa_name " +
                "from films as f " +
                "join mpa on f.mpa_id = mpa.id";

        return jdbcTemplate.query(sql, JdbcFilmRepository::createFilm);
    }

    @Override
    public Film getData(Long id) {
        String sql = "select f.id as id, f.name, f.description, f.release_date, f.rate, f.duration, " +
                "mpa.id as mpa_id, mpa.name as mpa_name " +
                "from films as f " +
                "join mpa on f.mpa_id = mpa.id " +
                "where f.id = ?";

        List<Film> films = jdbcTemplate.query(sql, JdbcFilmRepository::createFilm, id);

        if (films.size() != 1) {
            throw new NotFoundException(String.format("Film with id = %s is not single", id));
        }
        return films.get(0);
    }

    static Film createFilm(ResultSet rs, int rowNum) throws SQLException {

        Film film = Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .rate(rs.getInt("rate"))
                .duration(rs.getInt("duration"))
                .genres(new LinkedHashSet<>())
                .build();

        Mpa mpa = Mpa.builder()
                .id(rs.getLong("mpa_id"))
                .name(rs.getString("mpa_name"))
                .build();
        film.setMpa(mpa);

        return film;
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(START_RELEASE_DATE)) {
            log.info("Film release date is invalid: {}.", film.getReleaseDate());
            throw new ValidationException("Film release date is invalid.");
        }
    }
}
