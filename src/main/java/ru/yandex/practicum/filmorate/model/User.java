package ru.yandex.practicum.filmorate.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Email(message = "Email не корректен")
    @NotEmpty
    @Column(name = "EMAIL")
    private String email;

    @NotNull
    @Pattern(regexp = "^\\S*$", message = "Строка не должна содержать пробел")
    @Column(name = "LOGIN")
    private String login;

    @Column(name = "NAME")
    private String name;

    @NotNull
    @PastOrPresent
    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<UserFriend> friends;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID", referencedColumnName = "USER_ID")
    @JsonBackReference
    private LikeFilm film;
}

