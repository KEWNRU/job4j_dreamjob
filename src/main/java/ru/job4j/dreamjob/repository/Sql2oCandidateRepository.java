package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oCandidateRepository implements CandidateRepository {
    private final Sql2o sql2o;

    public Sql2oCandidateRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Candidate save(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var sql = """
                      INSERT INTO candidates (title, description, creation_date, visible, file_id)
                      VALUES (:title, :description, :creationDate, :visible, :fileId)
                      """;
            var query = connection.createQuery(sql, true)
                    .addParameter("title", candidate.getTitle())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("creationDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                    .addParameter("fileId", candidate.getFileId())
                    .addParameter("visible", candidate.getVisible());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            candidate.setId(generatedId);
            return candidate;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM candidates WHERE id = :id");
            query.addParameter("id", id);
            return query.executeUpdate().getResult() > 0;
        }
    }

    @Override
    public boolean update(Candidate candidate) {
        try (var connection = sql2o.open()) {
            var sql = """
                    UPDATE candidates
                    SET title = :title, description = :description, creation_date = :creationDate,
                        visible = :visible,  file_id = :fileId
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql)
                    .addParameter("title", candidate.getTitle())
                    .addParameter("description", candidate.getDescription())
                    .addParameter("creationDate", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                    .addParameter("visible", candidate.getVisible())
                    .addParameter("fileId", candidate.getFileId())
                    .addParameter("id", candidate.getId());
            return query.executeUpdate().getResult() > 0;
        }
    }

    @Override
    public Optional<Candidate> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates WHERE id = :id");
            query.addParameter("id", id);
            var candidate = query.setColumnMappings(Candidate.COLUMN_MAPPING).executeAndFetchFirst(Candidate.class);
            return Optional.ofNullable(candidate);
        }
    }

    @Override
    public Collection<Candidate> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM candidates");
            return query.setColumnMappings(Candidate.COLUMN_MAPPING).executeAndFetch(Candidate.class);
        }
    }
}