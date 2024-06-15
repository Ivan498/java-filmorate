package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.key.GenreFilmPK;

import javax.persistence.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "GENRE_FILM")
@IdClass(GenreFilmPK.class)
public class GenreFilm {

    @Id
    @Column(name = "FILM_ID")
    private Integer filmId;

    @Id
    @Column(name = "GENRE_ID")
    private Integer genreId;

    @OneToOne(mappedBy = "genreFilm", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Genre genre;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Film film;

}
