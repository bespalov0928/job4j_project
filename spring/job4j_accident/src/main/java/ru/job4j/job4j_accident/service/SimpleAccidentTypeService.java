package ru.job4j.job4j_accident.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.repository.MemAccidentTypeRepository;
import ru.job4j.job4j_accident.repository.jpa.JpaAccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService {

    //    private final MemAccidentTypeRepository repository;
    private final JpaAccidentTypeRepository repository;

    public Optional<AccidentType> findById(int id) {
        return repository.findById(id);
    }

    public Collection<AccidentType> findAll() {
        return (Collection<AccidentType>) repository.findAll();
    }
}
