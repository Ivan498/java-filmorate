package ru.yandex.practicum.filmorate.dao.memory;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.BaseUnit;
import ru.yandex.practicum.filmorate.dao.AbstractRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InMemoryBaseRepository<T extends BaseUnit> implements AbstractRepository<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private Long generatedId = 0L;

    @Override
    public T create(T data) {
        validate(data);
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public T update(T data) {
        if (!storage.containsKey(data.getId())) {
            throw new NotFoundException(String.format("%s not found.", data));
        }
        validate(data);
        storage.put(data.getId(), data);
        return data;
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T getData(Long id) {
        if (!storage.containsKey(id)) {
            throw new NotFoundException(String.format("id = %s not found.", id));
        }
        return storage.get(id);
    }

    public abstract void validate(T data);
}