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
        save(new Candidate(0, "Иван", "test", 0, true));
        save(new Candidate(0, "Наташа", "test", 0, true));
        save(new Candidate(0, "Владимир", "test", 0, true));
        save(new Candidate(0, "Сергей", "test", 0, true));
    }


    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidate.setData(LocalDateTime.now());
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
                (id, oldCandidates) -> new Candidate(oldCandidates.getId(), candidate.getName(),
                        candidate.getDescription(),
                        candidate.getFileId(), candidate.getVisible())) != null;
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
