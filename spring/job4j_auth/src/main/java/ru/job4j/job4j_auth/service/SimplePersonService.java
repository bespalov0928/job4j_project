package ru.job4j.job4j_auth.service;


import org.springframework.stereotype.Service;
import ru.job4j.job4j_auth.model.Person;
import ru.job4j.job4j_auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SimplePersonService implements PersonService {

    private PersonRepository personRepository;

    public SimplePersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return (List<Person>) this.personRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        Optional<Person> person = this.personRepository.findById(id);
        return person;
    }

    public Person save(Person person) {
        Person rsl = this.personRepository.save(person);
        return rsl;
    }

    public boolean update(Person person) {
        Optional<Person> personOptional = findById(person.getId());
        Person personOld = personOptional.get();
        personOld.setLogin(person.getLogin());
        personOld.setPassword(person.getPassword());
        this.personRepository.save(personOld);
        return true;
    }

    public boolean delete(int id) {
        Person person = new Person();
        person.setId(id);
        this.personRepository.delete(person);
        return true;
    }

}
