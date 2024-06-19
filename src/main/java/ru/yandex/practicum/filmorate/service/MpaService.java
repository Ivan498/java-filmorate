package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.dao.MpaRepository;

import java.util.List;

@Service
public class MpaService {
    MpaRepository mpaStorage;

    @Autowired
    public MpaService(MpaRepository mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    public Mpa getData(Long id) {
        return mpaStorage.getData(id);
    }
}
