package ru.job4j.dreamjob.repository;


import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import java.util.*;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Tom", "descriptionTom", true, 1));
        save(new Candidate(0, "Patric", "descriptionPatric", true, 2));
        save(new Candidate(0, "Sara", "descriptionSara", true, 3));
        save(new Candidate(0, "Mikle", "descriptionMikle", true, 1));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldVacancy) -> new Candidate(oldVacancy.getId(), candidate.getTitle(), candidate.getDescription(), candidate.isVisible(), candidate.getCityId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }

}
