package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
@NoArgsConstructor(force = true)
@Entity
@Table
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Integer duration;

    @ManyToMany(mappedBy = "likeFilms")
    private Set<User> likes;

    @ManyToMany(mappedBy = "films")
    private Set<Genre> genres;

    @ManyToMany(mappedBy = "films")
    private Set<Mpa> mpas;
}