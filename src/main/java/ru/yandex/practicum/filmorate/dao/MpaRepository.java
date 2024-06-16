package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;
import java.util.List;

public interface MpaRepository extends AbstractRepository<Mpa> {
    List<Mpa> getAll();

    Mpa getData(Long id);

    Mpa create(Mpa data);

    Mpa update(Mpa data);
}