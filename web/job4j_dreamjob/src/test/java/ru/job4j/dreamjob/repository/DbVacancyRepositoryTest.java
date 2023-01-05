package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Main;
import ru.job4j.dreamjob.model.Vacancy;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

class DbVacancyRepositoryTest {

    @Test
    void save() {
        VacancyRepository store = new DbVacancyRepository(new Main().loadPool());
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        store.save(vacancy);
        Vacancy vacancyInDb = store.findById(vacancy.getId()).get();
        assertThat(vacancyInDb.getTitle(), is(vacancy.getTitle()));
        System.out.println("save");
    }

//    @Test
//    void deleteById() {
//    }

//    @Test
//    void update() {
//    }

//    @Test
//    void findById() {
//    }

//    @org.junit.jupiter.api.Test
//    void findAll() {
//    }
}