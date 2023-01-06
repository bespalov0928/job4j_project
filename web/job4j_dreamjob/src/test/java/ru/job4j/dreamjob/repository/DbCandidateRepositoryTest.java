package ru.job4j.dreamjob.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Candidate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class DbCandidateRepositoryTest {

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
             PreparedStatement ps = cn.prepareStatement("delete from candidate")) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {
        CandidateRepository store = new DbCandidateRepository(pool);
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        store.save(candidate);
        Candidate candidateInDb = store.findById(candidate.getId()).get();
        assertThat(candidateInDb.getTitle(), is(candidate.getTitle()));
    }

    @Test
    void deleteById() {
        CandidateRepository store = new DbCandidateRepository(pool);
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        store.save(candidate);
        store.deleteById(candidate.getId());
        assertThat(store.findAll().size(), is(0));
    }

    @Test
    void update() {
        CandidateRepository store = new DbCandidateRepository(pool);
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        store.save(candidate);
        Candidate candidateInDb = store.findById(candidate.getId()).get();
        candidateInDb.setTitle("Intern Java Developer middle");
        store.update(candidateInDb);
        Candidate candidateInDbUpdate =  store.findById(candidate.getId()).get();
        assertThat(candidateInDbUpdate.getTitle(), is("Intern Java Developer middle"));
    }

    @Test
    void findById() {
        CandidateRepository store = new DbCandidateRepository(pool);
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        store.save(candidate);
        Candidate candidateInDb = store.findById(candidate.getId()).get();
        assertThat(candidateInDb.getTitle(), is("Tom"));

    }

    @Test
    void findAll() {
        CandidateRepository store = new DbCandidateRepository(pool);
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        store.save(candidate);
        Collection<Candidate> list = store.findAll();
        assertThat(list.size(), is(1));
    }
}