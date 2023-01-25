package ru.job4j.job4j_accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.AccidentType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MemAccidentTypeRepository {
    private final List<AccidentType> types = new ArrayList<>();

    public MemAccidentTypeRepository() {
        types.add(new AccidentType(0, "Две машины"));
        types.add(new AccidentType(1, "Машина и человек"));
        types.add(new AccidentType(2, "Машина и велосипед"));
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.of(types.get(id));
    }

    public Collection<AccidentType> findAll() {
        return types;
    }
}
