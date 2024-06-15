package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.JpaMpaRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class MpaService {
    JpaMpaRepository jpaMpaRepository;

    @Autowired
    public MpaService(JpaMpaRepository jpaMpaRepository) {
        this.jpaMpaRepository = jpaMpaRepository;
    }

    public Collection<Mpa> getAllMpa() {
        return jpaMpaRepository.findAll();
    }

    public Mpa getMpaById(Integer id) {
        Optional<Mpa> optionalMpa = jpaMpaRepository.findById(id);
        if (optionalMpa.isPresent()) {
            return optionalMpa.get();
        } else {
            throw new NotFoundException("Mpa с ID " + id + " не найден");
        }
    }
}
