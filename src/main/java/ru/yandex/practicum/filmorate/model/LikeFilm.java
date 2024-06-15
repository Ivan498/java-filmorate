package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.model.key.LikeFilmPK;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LIKE_FILM")
@IdClass(LikeFilmPK.class)
public class LikeFilm {

    @Id
    @Column(name = "FILM_ID")
    private Integer filmId;

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

    @OneToOne(mappedBy = "likeFilm", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    @JsonBackReference
    private Film film;

}
