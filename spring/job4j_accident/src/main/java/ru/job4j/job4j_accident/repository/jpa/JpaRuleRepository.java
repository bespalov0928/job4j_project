package ru.job4j.job4j_accident.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_accident.model.Rule;

public interface JpaRuleRepository extends CrudRepository<Rule, Integer> {
}
