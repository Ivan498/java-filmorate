package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class MpaService {
    MpaRepository mpaRepository;

    @Autowired
    public MpaService(MpaRepository mpaRepository) {
        this.mpaRepository = mpaRepository;
    }

    public Collection<Mpa> getAllMpa() {
        return mpaRepository.findAll();
    }

    public Mpa getMpaById(Integer id) {
        Optional<Mpa> optionalMpa = mpaRepository.findById(id);
        if (optionalMpa.isPresent()) {
            return optionalMpa.get();
        } else {
            throw new DataNotFoundException("Mpa с ID " + id + " не найден");
        }
    }
}
