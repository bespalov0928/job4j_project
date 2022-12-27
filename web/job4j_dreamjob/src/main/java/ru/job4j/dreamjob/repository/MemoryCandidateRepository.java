package ru.job4j.dreamjob.repository;


import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.*;

public class MemoryCandidateRepository implements CandidateRepository {
    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public MemoryCandidateRepository() {
        save(new Candidate(0, "Tom", "descriptionTom", new Date()));
        save(new Candidate(0, "Patric", "descriptionPatric", new Date()));
        save(new Candidate(0, "Sara", "descriptionSara", new Date()));
        save(new Candidate(0, "Mikle", "descriptionMikle", new Date()));
    }

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
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
    public boolean update(Candidate vacancy) {
        return candidates.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Candidate(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(), vacancy.getCreationDate())) != null;
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
