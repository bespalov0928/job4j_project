package ru.job4j.job4j_accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.model.Rule;
import ru.job4j.job4j_accident.repository.MemRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleRuleService {
    private final MemRuleRepository ruleRepository;

    public SimpleRuleService(MemRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

}
