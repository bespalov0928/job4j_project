package ru.job4j.job4j_accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.repository.MemAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleAccidentTypeService {

    private final MemAccidentTypeRepository repository;

    public SimpleAccidentTypeService(MemAccidentTypeRepository repository) {
        this.repository = repository;
    }

    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return repository.findAll();
    }
}
