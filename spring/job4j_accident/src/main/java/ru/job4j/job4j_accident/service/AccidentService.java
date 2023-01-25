package ru.job4j.job4j_accident.service;

import ru.job4j.job4j_accident.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    public Accident save(Accident accident);

    boolean deleteById(int id);

    boolean update(Accident accident);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();

}