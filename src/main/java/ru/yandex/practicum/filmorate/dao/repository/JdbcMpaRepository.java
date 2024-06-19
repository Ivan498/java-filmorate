package ru.yandex.practicum.filmorate.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.MpaRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcMpaRepository implements MpaRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMpaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "select * from mpa";
        return jdbcTemplate.query(sqlQuery, this::createMpa);
    }

    @Override
    public Mpa getData(Long id) {
        String sqlQuery = "select * from mpa where id = ?";
        List<Mpa> mpas = jdbcTemplate.query(sqlQuery, this::createMpa, id);
        if (mpas.size() != 1) {
            throw new NotFoundException(String.format("Mpa with id = %s is not single", id));
        }
        return mpas.get(0);
    }

    @Override
    public Mpa create(Mpa mpa) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mpa update(Mpa mpa) {
        throw new UnsupportedOperationException();
    }

    private Mpa createMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
