package ru.job4j.job4j_accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.repository.MemAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleAccidentService implements AccidentService {

    private final MemAccidentRepository accidentMem;
    public SimpleAccidentService(MemAccidentRepository accidentMem) {
        this.accidentMem = accidentMem;
    }

    @Override
    public Accident save(Accident accident) {
        accidentMem.save(accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Accident accident) {
        boolean rsl = accidentMem.update(accident);
        return rsl;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }
}
