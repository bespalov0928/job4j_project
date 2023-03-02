package ru.job4j.job4j_auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.job4j.job4j_auth.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> findAll();
    Optional<Person> findById(int id);
    Person save(Person person);
    boolean update(Person person);
    boolean delete(int id);
}
