package ru.yandex.practicum.filmorate;

import org.springframework.jdbc.core.JdbcTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import ru.yandex.practicum.filmorate.dao.repository.JdbcFilmRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.LinkedHashSet;

@JdbcTest
public class JdbcFilmRepositoryTest {

    private JdbcTemplate jdbcTemplate;

    public JdbcFilmRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    public void testCreateAndFindFilmById() {
        Mpa mpa = Mpa.builder()
                .id(1L)
                .name("PG-13")
                .build();

        Film newFilm = Film.builder()
                .id(1L)
                .name("NF")
                .description("dsdassads")
                .releaseDate(LocalDate.of(2020, 1, 1))
                .duration(100)
                .rate(3)
                .mpa(mpa)
                .genres(new LinkedHashSet<>())
                .build();
        JdbcFilmRepository jdbcFilmRepository = new JdbcFilmRepository(jdbcTemplate);
        jdbcFilmRepository.create(newFilm);

        Film savedFilm = jdbcFilmRepository.getData(1L);

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }
}
