package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.key.MpaFilmPK;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "MPA_FILM")
@IdClass(MpaFilmPK.class)
public class MpaFilm {

    @Id
    @Column(name = "MPA_ID")
    private Integer mpaId;

    @Id
    @Column(name = "FILM_ID")
    private Integer filmId;

    @OneToOne(mappedBy = "mpaFilm", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Mpa mpa;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Film film;

}
