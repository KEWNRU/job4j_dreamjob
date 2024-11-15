package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {
    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("INSERT INTO users (name, email, password) VALUES (:name, :email, :password)", true)
                    .addParameter("name", user.getName())
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
        } catch (Sql2oException e) {
            if (e.getMessage().contains("duplicate key") || e.getMessage().contains("Нарушение уникального индекса или первичного ключа")) {
                throw new DuplicateKeyException("Пользовотель с такой почтой зарегестрирован");
            }
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email and password = :password")
                    .addParameter("email", email)
                    .addParameter("password", password);
            return Optional.ofNullable(query.executeAndFetchFirst(User.class));

        }
    }

    @Override
    public boolean deleteByEmail(String email) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE email = :email");
            query.addParameter("email", email);
            return query.executeUpdate().getResult() > 0;
        }
    }

    @Override
    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING).executeAndFetch(User.class);
        }
    }
    public static class DuplicateKeyException extends RuntimeException {
        public DuplicateKeyException(String message) {
            super(message);
        }
    }

}
