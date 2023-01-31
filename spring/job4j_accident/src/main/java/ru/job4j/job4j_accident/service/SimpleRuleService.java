package ru.job4j.job4j_accident.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.model.Rule;
import ru.job4j.job4j_accident.repository.MemRuleRepository;
import ru.job4j.job4j_accident.repository.hbm.HbmRuleRepository;
import ru.job4j.job4j_accident.repository.jpa.JpaRuleRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleRuleService {
//    private final MemRuleRepository ruleRepository;
//    private final HbmRuleRepository ruleRepository;
    private final JpaRuleRepository ruleRepository;

    public Optional<Rule> findById(int id) {
        return ruleRepository.findById(id);
    }

    public Collection<Rule> findAll() {
        return (Collection<Rule>) ruleRepository.findAll();
    }

}
