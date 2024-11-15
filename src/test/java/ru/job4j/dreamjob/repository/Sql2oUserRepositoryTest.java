package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oCandidateRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);

    }

    @AfterEach
    public void clearDb() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteByEmail(user.getEmail());
        }
    }

    @Test
    public void whenSaveOneUser() {
        var user = sql2oUserRepository.save(new User(0, "Kewn21rus", "Ivan", "n0kky21rus@gmail.com"));
        var rsl = sql2oUserRepository.findByEmailAndPassword("n0kky21rus@gmail.com", "Kewn21rus");
        assertThat(rsl).isEqualTo(user);
    }

    @Test
    public void whenSavingUsersWithSameEmail() {
        var user = sql2oUserRepository.save(new User(0, "Kewn21rus", "Ivan", "n0kky21rus@gmail.com"));
        var userSave = sql2oUserRepository.findByEmailAndPassword("n0kky21rus@gmail.com", "Kewn21rus");
        assertThat(userSave).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSearchByEmailAndPassword() {
        var user = sql2oUserRepository.save(new User(0, "Kewn21rus", "Ivan", "n0kky21rus@gmail.com"));
        var rsl = sql2oUserRepository.findByEmailAndPassword("n0kky21rus@gmail.com", "Kewn21rus");
        assertThat(user).isEqualTo((rsl));
    }

    @Test
    public void whenDuplicateUserThrowsException() {
        User user = new User(0, "Kewn21rus", "Ivan", "n0kky21rus@gmail.com");
        sql2oUserRepository.save(user);
        assertThatThrownBy(() -> sql2oUserRepository.save(user))
                .isInstanceOf(Sql2oUserRepository.DuplicateKeyException.class)
                .hasMessageContaining("Пользовотель с такой почтой зарегестрирован");
    }
}
