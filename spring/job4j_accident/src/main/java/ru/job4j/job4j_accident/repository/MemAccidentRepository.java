package ru.job4j.job4j_accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;

import java.util.*;


@Repository
public class MemAccidentRepository implements AccidentRepository {
    private int nextId = 1;
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final MemAccidentTypeRepository repositoryTypes;

    public MemAccidentRepository(MemAccidentTypeRepository repositoryTypes) {
        this.repositoryTypes = repositoryTypes;
        save(new Accident(0, "name1", "text1", "address1", repositoryTypes.findById(0).get()));
        save(new Accident(0, "name2", "text2", "address2", repositoryTypes.findById(1).get()));
        save(new Accident(0, "name3", "text3", "address3", repositoryTypes.findById(2).get()));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId++);
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldVacancy) -> new Accident(oldVacancy.getId(), accident.getName(), accident.getText(), accident.getAddress(), accident.getType())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        var rsl = accidents.get(id);
        return Optional.of(rsl);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
