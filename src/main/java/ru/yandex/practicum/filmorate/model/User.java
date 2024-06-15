package ru.yandex.practicum.filmorate.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email()
    @NotEmpty
    private String email;

    @NotNull
    @Pattern(regexp = "^\\S*$")
    private String login;

    private String name;

    @NotNull
    @PastOrPresent
    private LocalDate birthday;

    @OneToMany
    private List<User> friends;

    @ManyToMany
    @JoinTable(name = "LIKES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> likeFilms;
}

