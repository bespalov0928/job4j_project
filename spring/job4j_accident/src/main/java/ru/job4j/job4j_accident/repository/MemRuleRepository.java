package ru.job4j.job4j_accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class MemRuleRepository {
    private final List<Rule> rules = new ArrayList<>();

    public MemRuleRepository() {
        rules.add(new Rule(0, "Статья. 1"));
        rules.add(new Rule(1, "Статья. 2"));
        rules.add(new Rule(2, "Статья. 3"));
    }

    public Optional<Rule> findById(int id) {
        return Optional.of(rules.get(id));
    }

    public Collection<Rule> findAll() {
        return rules;
    }

}
