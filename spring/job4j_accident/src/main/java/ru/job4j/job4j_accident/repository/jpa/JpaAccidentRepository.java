package ru.job4j.job4j_accident.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_accident.model.Accident;

import java.util.List;

public interface JpaAccidentRepository extends CrudRepository<Accident, Integer> {
    @Query(value = "select a from Accident a join fetch a.type join fetch a.rules")
    List<Accident> findAll();
}
