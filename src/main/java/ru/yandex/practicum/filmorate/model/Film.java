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

    @NotNull
    @NotBlank
    private String name;

    @Size(min = 1, max = 200,message = "Должно быть больше 1 и меньше 200")
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Min(value = 1,message = "Должно быть положительное число")
    private Integer duration;

    @ManyToMany(mappedBy = "likeFilms")
    private Set<User> likes;

    @ManyToMany(mappedBy = "films")
    private Set<Genre> genres;

    @ManyToOne
    private Mpa mpa;
}