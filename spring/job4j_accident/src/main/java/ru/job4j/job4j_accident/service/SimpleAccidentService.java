package ru.job4j.job4j_accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.repository.MemAccidentRepository;
import ru.job4j.job4j_accident.repository.MemAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleAccidentService implements AccidentService {

    private final MemAccidentRepository accidentRepository;
    private final MemAccidentTypeRepository typeRepository;
    public SimpleAccidentService(MemAccidentRepository accidentRepository, MemAccidentTypeRepository typeRepository) {
        this.accidentRepository = accidentRepository;
        this.typeRepository = typeRepository;
    }

    @Override
    public Accident save(Accident accident) {
        Optional<AccidentType> accidentType = typeRepository.findById(accident.getType().getId());
        accident.setType(accidentType.get());
        accidentRepository.save(accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Accident accident) {
        Optional<AccidentType> accidentType = typeRepository.findById(accident.getType().getId());
        accident.setType(accidentType.get());
        boolean rsl = accidentRepository.update(accident);
        return rsl;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }
}
