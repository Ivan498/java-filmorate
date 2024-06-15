package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor(force = true)
@Entity
@Table
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @Size(min = 1, max = 200)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Min(value = 1)
    private Integer duration;

    @ManyToMany(mappedBy = "likeFilms")
    private Set<User> likes;

    @ManyToMany(mappedBy = "films")
    private Set<Genre> genres;

    @ManyToOne
    private Mpa mpa;
}