package ru.job4j.job4j_accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.model.Rule;
import ru.job4j.job4j_accident.repository.MemRuleRepository;
import ru.job4j.job4j_accident.repository.hbm.HbmRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleRuleService {
//    private final MemRuleRepository ruleRepository;
    private final HbmRuleRepository ruleRepository;

    public SimpleRuleService(HbmRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Collection<Rule> findAll() {
        return ruleRepository.findAll();
    }

}
