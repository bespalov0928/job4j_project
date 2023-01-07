package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.VacancyService;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VacancyControllerTest {

    @Test
    public void getAll() {
        List<Vacancy> vacancyList = Arrays.asList(
                new Vacancy(0, "Intern Java Developer", "description1", true, 1),
                new Vacancy(1, "Junior Java Developer", "description2", true, 1)
        );
        Model model = mock(Model.class);
        VacancyService vacancyService = mock(VacancyService.class);
        when(vacancyService.findAll()).thenReturn(vacancyList);
        CityService cityService = mock(CityService.class);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);

        String page = vacancyController.getAll(model);
        verify(model).addAttribute("vacancies", vacancyList);
        assertThat(page, is("vacancies/list"));
    }

    @Test
    public void getCreationPage() {
        Collection<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );
        Model model = mock(Model.class);
        VacancyService vacancyService = mock(VacancyService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findAll()).thenReturn(cities);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);

        String page = vacancyController.getCreationPage(model);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("vacancies/create"));
    }

    @Test
    public void create() {
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        VacancyService vacancyService = mock(VacancyService.class);
        CityService cityService = mock(CityService.class);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);

        String page = vacancyController.create(vacancy);
        assertThat(page, is("redirect:/vacancies"));
    }

    @Test
    public void getById() {
        Collection<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );

        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        Optional<Vacancy> optionalVacancy = Optional.of(vacancy);
        Model model = mock(Model.class);
        VacancyService vacancyService = mock(VacancyService.class);
        CityService cityService = mock(CityService.class);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);
        when(vacancyService.findById(0)).thenReturn(optionalVacancy);
        when(cityService.findAll()).thenReturn(cities);

        String page = vacancyController.getById(model, 0);
        verify(model).addAttribute("cities", cities);
        verify(model).addAttribute("vacancy", vacancy);
        assertThat(page, is("vacancies/edit"));
    }

    @Test
    public void update() {
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);

        Model model = mock(Model.class);
        VacancyService vacancyService = mock(VacancyService.class);
        CityService cityService = mock(CityService.class);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);
        when(vacancyService.update(vacancy)).thenReturn(true);

        String page = vacancyController.update(vacancy, model);
        assertThat(page, is("redirect:/vacancies"));
    }

    @Test
    public void delete() {
        Vacancy vacancy = new Vacancy(0, "Intern Java Developer", "description1", true, 1);
        Model model = mock(Model.class);
        VacancyService vacancyService = mock(VacancyService.class);
        CityService cityService = mock(CityService.class);
        VacancyController vacancyController = new VacancyController(vacancyService, cityService);
        when(vacancyService.deleteById(0)).thenReturn(true);

        String page = vacancyController.delete(model, 0);
        assertThat(page, is("redirect:/vacancies"));
    }

}