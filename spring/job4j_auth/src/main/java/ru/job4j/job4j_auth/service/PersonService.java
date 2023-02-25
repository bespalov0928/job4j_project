package ru.job4j.job4j_auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.job4j.job4j_auth.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    public List<Person> findAll();
    public Optional<Person> findById(int id);
    public Person save(Person person);
    public boolean update(Person person);
    public boolean delete(int id);
}
