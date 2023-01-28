package ru.job4j.job4j_accident.repository.hbm;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.job4j_accident.model.AccidentType;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmAccidentTypeRepository {
    private final SessionFactory sf;

    public Optional<AccidentType> findById(int id) {
        try (Session session = sf.openSession()) {
            var rsl = session.get(AccidentType.class, id);
            return Optional.of(rsl);
        }

    }

    public Collection<AccidentType> findAll() {
        try (Session session = sf.openSession()) {
            List<AccidentType> rsl = session.createQuery("from AccidentType", AccidentType.class).list();
            return rsl;
        }
    }

}
