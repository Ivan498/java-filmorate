package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
@Table
public class Mpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Integer id;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "MPA_FILM",
            joinColumns = @JoinColumn(name = "MPA_ID"),
            inverseJoinColumns = @JoinColumn(name = "FILM_ID")
    )
    private Set<Film> films;
}
