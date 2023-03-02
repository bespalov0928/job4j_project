package ru.job4j.job4j_auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.job4j_auth.model.Person;
import ru.job4j.job4j_auth.service.PersonService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;
    private final BCryptPasswordEncoder passwordEncoder;

    public PersonController(PersonService personService, BCryptPasswordEncoder passwordEncoder) {
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public ResponseEntity<List<Person>> findAll() {
        List<Person> rsl = this.personService.findAll();
//        ResponseEntity<List<Person>> entity = new ResponseEntity<List<Person>>(
//                rsl,
//                rsl.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK
//        );
        ResponseEntity<List<Person>> entityNew = ResponseEntity.ok().body(rsl);
        return entityNew;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> rsl = this.personService.findById(id);
//        ResponseEntity<Person> entity = new ResponseEntity<Person>(
//                rsl.orElse(new Person()),
//                rsl.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        ResponseEntity<Person> entityNew = ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rsl.get());
        return entityNew;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person rsl = this.personService.save(person);
//        ResponseEntity<Person> response = new ResponseEntity<Person>(
//                rsl,
//                HttpStatus.CREATED
//        );
        ResponseEntity<Person> responseNew = ResponseEntity.status(HttpStatus.CREATED).body(rsl);
        return responseNew;
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        boolean rsl = this.personService.update(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean rsl = this.personService.delete(id);
        return rsl ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        this.personService.save(person);
    }

}
