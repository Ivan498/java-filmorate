package ru.yandex.practicum.filmorate.dao.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.GenreRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcGenreRepository implements GenreRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "select * from genres";
        return jdbcTemplate.query(sqlQuery, this::createGenre);
    }

    @Override
    public Genre getData(Long id) {
        String sqlQuery = "select * from genres where id = ?";
        List<Genre> genres = jdbcTemplate.query(sqlQuery, this::createGenre, id);
        if (genres.size() != 1) {
            throw new NotFoundException(String.format("Genre with id = %s is not single", id));
        }
        return genres.get(0);
    }

    @Override
    public Genre create(Genre genre) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Genre update(Genre genre) {
        throw new UnsupportedOperationException();
    }

    private Genre createGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}