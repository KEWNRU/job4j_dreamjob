package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.*;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Иван", "test", true, 0));
        save(new Candidate(0, "Наташа", "test", true, 0));
        save(new Candidate(0, "Владимир", "test", true, 0));
        save(new Candidate(0, "Сергей", "test", true, 0));
    }


    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidate.setCreationDate(LocalDateTime.now());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
       return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(),
                (id, oldCandidates) -> new Candidate(oldCandidates.getId(), candidate.getTitle(),
                        candidate.getDescription(),
                         candidate.getVisible(), candidate.getFileId())) != null;
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
