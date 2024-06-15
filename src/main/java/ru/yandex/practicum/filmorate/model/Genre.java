package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "GENRE")
public class Genre {

    @Id
    @Column(name = "FILM_ID")
    private Integer filmId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumns(value = {
            @JoinColumn(name = "FILM_ID", referencedColumnName = "FILM_ID"),
            @JoinColumn(name = "ID", referencedColumnName = "GENRE_ID")
    })
    @JsonBackReference
    private GenreFilm genreFilm;

}
