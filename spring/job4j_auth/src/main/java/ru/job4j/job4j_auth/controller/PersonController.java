package ru.job4j.job4j_auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.job4j_auth.model.Person;
import ru.job4j.job4j_auth.service.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public List<Person> findAll() {
        var rsl = this.personService.findAll();
        return rsl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> rsl = this.personService.findById(id);
        ResponseEntity<Person> entity = new ResponseEntity<Person>(
                rsl.orElse(new Person()),
                rsl.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        return entity;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = this.personService.save(person);
        return new ResponseEntity<Person>(
                rsl,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        Person rsl = this.personService.save(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var rsl = this.personService.delete(id);
        return rsl ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
