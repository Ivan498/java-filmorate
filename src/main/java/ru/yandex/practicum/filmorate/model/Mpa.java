package ru.yandex.practicum.filmorate.model;

import javax.persistence.*;

import lombok.Data;


@Data
@Entity
@Table
public class Mpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
