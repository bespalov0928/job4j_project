package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Vacancy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class DbVacancyRepositoryTest {

    private static BasicDataSource pool;

    @BeforeAll
    public static void loadPool() {
        Properties cfg = loadDbProperties();
        pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("driver-class-name"));
        pool.setUrl(cfg.getProperty("url"));
        pool.setUsername(cfg.getProperty("username"));
        pool.setPassword(cfg.getProperty("password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("liquibase_test.properties")))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("driver-class-name"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    @AfterEach
    public void wipeTable() {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("delete from vacancy")) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {
        VacancyRepository store = new DbVacancyRepository(pool);
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        Vacancy vacancyInDb = store.findById(vacancy.getId()).get();
        assertThat(vacancyInDb.getTitle(), is(vacancy.getTitle()));
    }

    @Test
    void deleteById() {
        VacancyRepository store = new DbVacancyRepository(pool);
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        store.deleteById(vacancy.getId());
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    void update() {
        VacancyRepository store = new DbVacancyRepository(pool);
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        Vacancy vacancyInDb = store.findById(vacancy.getId()).get();
        vacancyInDb.setTitle("Intern Java Developer middle");
        store.update(vacancyInDb);
        Vacancy vacancyInDbUpdate =  store.findById(vacancy.getId()).get();
        assertThat(vacancyInDbUpdate.getTitle(), is("Intern Java Developer middle"));
    }

    @Test
    void findById() {
        VacancyRepository store = new DbVacancyRepository(pool);
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        Vacancy vacancyInDb = store.findById(vacancy.getId()).get();
        assertThat(vacancyInDb.getTitle(), is("Intern Java Developer"));

    }

    @Test
    void findAll() {
        VacancyRepository store = new DbVacancyRepository(pool);
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        Collection<Vacancy> list = store.findAll();
        assertThat(list.size(), is(1));
    }
}