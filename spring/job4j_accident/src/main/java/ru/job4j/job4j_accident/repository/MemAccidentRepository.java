package ru.job4j.job4j_accident.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.Rule;

import java.util.*;


@Repository
public class MemAccidentRepository implements AccidentRepository {
    private int nextId = 1;
    private final Map<Integer, Accident> accidents = new HashMap<>();
    private final MemAccidentTypeRepository repositoryTypes;
    private final MemRuleRepository ruleRepository;

    public MemAccidentRepository(MemAccidentTypeRepository repositoryTypes, MemRuleRepository ruleRepository) {
        this.repositoryTypes = repositoryTypes;
        this.ruleRepository = ruleRepository;

        List<Rule> ruleSet0 = List.of(ruleRepository.findById(0).get());
        List<Rule> ruleSet1 = List.of(ruleRepository.findById(1).get());
        List<Rule> ruleSet2 = List.of(ruleRepository.findById(2).get());
        save(new Accident(0, "name1", "text1", "address1", repositoryTypes.findById(0).get(), ruleSet0));
        save(new Accident(0, "name2", "text2", "address2", repositoryTypes.findById(1).get(), ruleSet1));
        save(new Accident(0, "name3", "text3", "address3", repositoryTypes.findById(2).get(), ruleSet2));
    }

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId++);
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldVacancy) -> new Accident(oldVacancy.getId(), accident.getName(), accident.getText(), accident.getAddress(), accident.getType(), accident.getRules())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        var rsl = accidents.get(id);
        return Optional.of(rsl);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
