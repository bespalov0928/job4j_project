package ru.job4j.job4j_accident.repository.hbm;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.Rule;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmRuleRepository {

    private SessionFactory sf;

    public Optional<Rule> findById(int id) {
        try (Session session = sf.openSession()) {
            Rule rsl = session.get(Rule.class, id);
            return Optional.of(rsl);
        }

    }

    public Collection<Rule> findAll() {
        try (Session session = sf.openSession()) {
            List<Rule> rsl = session.createQuery("from Rule", Rule.class).list();
            return rsl;
        }
    }
}
