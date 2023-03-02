package ru.job4j.job4j_accident.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.model.Rule;
import ru.job4j.job4j_accident.repository.MemAccidentRepository;
import ru.job4j.job4j_accident.repository.MemAccidentTypeRepository;
import ru.job4j.job4j_accident.repository.MemRuleRepository;
import ru.job4j.job4j_accident.repository.hbm.HbmAccidentRepository;
import ru.job4j.job4j_accident.repository.hbm.HbmAccidentTypeRepository;
import ru.job4j.job4j_accident.repository.hbm.HbmRuleRepository;
import ru.job4j.job4j_accident.repository.jpa.JpaAccidentRepository;
import ru.job4j.job4j_accident.repository.jpa.JpaAccidentTypeRepository;
import ru.job4j.job4j_accident.repository.jpa.JpaRuleRepository;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

//    private final MemAccidentRepository accidentRepository;
//    private final MemAccidentTypeRepository typeRepository;
//    private final MemRuleRepository ruleRepository;

//    private final HbmAccidentRepository accidentRepository;
//    private final HbmAccidentTypeRepository typeRepository;
//    private final HbmRuleRepository ruleRepository;

    private final JpaAccidentRepository accidentRepository;
    private final JpaAccidentTypeRepository typeRepository;
    private final JpaRuleRepository ruleRepository;

    @Override
    public Accident save(Accident accident, String[] ids) {
        Integer[] arrInt = Stream.of(ids).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
        Optional<AccidentType> accidentType = typeRepository.findById(accident.getType().getId());
        accident.setType(accidentType.get());

        List<Rule> listRule = getRules(ids);
        accident.setRules(listRule);
        accidentRepository.save(accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Accident accident, String[] ids) {
        Optional<AccidentType> accidentType = typeRepository.findById(accident.getType().getId());
        accident.setType(accidentType.get());

        List<Rule> listRule = getRules(ids);
        accident.setRules(listRule);

//        boolean rsl = accidentRepository.update(accident);
        boolean rsl = true;
        accidentRepository.save(accident);
        return rsl;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }

    private List<Rule> getRules(String[] ids) {
        Integer[] arrInt = Stream.of(ids).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
        Supplier<List<Rule>> suppler = LinkedList::new;
        BiConsumer<List<Rule>, Integer> biConsumer = new BiConsumer<List<Rule>, Integer>() {
            @Override
            public void accept(List<Rule> rules, Integer integer) {
                var ruleRsl = ruleRepository.findById(integer).get();
                rules.add(ruleRsl);
            }
        };
        BinaryOperator<List<Rule>> operator = (left, right) -> {
            left.addAll(right);
            return left;
        };
        var listRule = Stream.of(arrInt).collect(Collector.of(suppler, biConsumer, operator));
        return listRule;

    }

}
