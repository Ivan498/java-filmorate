package ru.yandex.practicum.filmorate.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    @Email(message = "Email не корректен")
    @NotEmpty
    private String email;

    @NotNull
    @Pattern(regexp = "^\\S*$", message = "Строка не должна содержать пробел")
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

