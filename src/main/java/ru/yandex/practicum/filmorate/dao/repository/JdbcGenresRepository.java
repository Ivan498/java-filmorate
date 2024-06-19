package ru.yandex.practicum.filmorate.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.dao.GenresRepository;

import java.util.*;


@Component
public class JdbcGenresRepository implements GenresRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcGenresRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateGenresForFilm(Film film, Set<Genre> genres) {
        Long id = film.getId();

        String sqlRemoveOldGenres = "delete from film_genres where film_id = ?";
        jdbcTemplate.update(sqlRemoveOldGenres, id);

        if (genres.size() != 0) {
            String sqlAddNewGenres = "insert into film_genres values (?, ?)";
            for (Genre genre : genres) {
                jdbcTemplate.update(sqlAddNewGenres, id, genre.getId());
            }
        }
    }

    @Override
    public Set<Genre> getGenresForFilm(Long filmId) {
        String sql = "select g.id, g.name " +
                "from genres as g " +
                "join film_genres as f on f.genre_id = g.id " +
                "where film_id = ? " +
                "order by g.id";
        Set<Genre> genres = new LinkedHashSet<>();
        List<Map<String, Object>> genresList = jdbcTemplate.queryForList(sql, filmId);

        if (!genresList.isEmpty()) {
            for (Map<String, Object> line : genresList) {
                Genre genre = Genre.builder()
                        .id(Long.valueOf((Integer) line.get("id")))
                        .name((String) line.get("name"))
                        .build();
                genres.add(genre);
            }
        }
        return genres;
    }
}