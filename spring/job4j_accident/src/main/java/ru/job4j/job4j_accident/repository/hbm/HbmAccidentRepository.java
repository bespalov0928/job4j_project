package ru.job4j.job4j_accident.repository.hbm;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.repository.AccidentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentRepository implements AccidentRepository {

    private final HbmAccidentTypeRepository repositoryTypes;
    private final HbmRuleRepository ruleRepository;
    private final SessionFactory sf;


    @Override
    public Accident save(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(accident);
            session.getTransaction().commit();
        }
        return accident;
    }

    @Override
    public boolean deleteById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Accident rsl = (Accident) session.createQuery("delete from Accident a where a.id=:aId").setParameter("aId", id)
                    .getSingleResult();
            session.getTransaction().commit();
            return rsl.getId() == id;
        }

    }

    @Override
    public boolean update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Accident rsl = (Accident) session.createQuery("from Accident a JOIN FETCH a.type join FETCH a.rules where a.id=:aId", Accident.class)
                    .setParameter("aId", id)
                    .uniqueResult();
            session.getTransaction().commit();
            return Optional.of(rsl);
        }
    }

    @Override
    public Collection<Accident> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Accident> rsl = session.createQuery("from Accident", Accident.class).list();
            session.getTransaction().commit();
            return rsl;
        }
    }
}
