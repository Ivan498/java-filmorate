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
@NoArgsConstructor
@Entity
@Table(name = "FILM")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    @Size(min = 1, max = 200, message = "Должно быть больше 1 и меньше 200")
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "RELEASE_DATE")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Должно быть положительное число")
    @Column(name = "DURATION")
    private Integer duration;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<LikeFilm> likes;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<GenreFilm> genres;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<MpaFilm> mpa;

}