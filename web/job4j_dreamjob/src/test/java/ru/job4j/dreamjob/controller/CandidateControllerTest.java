package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CandidateControllerTest {

    @Test
    public void getAll() {
        Collection<Candidate> candidateList = Arrays.asList(
                new Candidate(0, "Tom", "descriptionTom", true, 1),
                new Candidate(0, "Patric", "descriptionPatric", true, 2)
        );
        Model model = mock(Model.class);
        CandidateService candidateService = mock(CandidateService.class);
        CityService cityService = mock(CityService.class);
        CandidateController candidateController = new CandidateController(candidateService, cityService);
        when(candidateService.findAll()).thenReturn(candidateList);

        String page = candidateController.getAll(model);
        verify(model).addAttribute("candidates", candidateList);
        assertThat(page, is("candidates/list"));
    }

    @Test
    public void getCreationPage() {
        Collection<City> cityCollection = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );
        Model model = mock(Model.class);
        CandidateService candidateService = mock(CandidateService.class);
        CityService cityService = mock(CityService.class);
        CandidateController candidateController = new CandidateController(candidateService, cityService);
        when(cityService.findAll()).thenReturn(cityCollection);

        String page = candidateController.getCreationPage(model);
        verify(model).addAttribute("cities", cityCollection);
        assertThat(page, is("candidates/create"));
    }

    @Test
    public void create() {
    }

    @Test
    public void getById() {
        Collection<City> cityCollection = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург"),
                new City(3, "Екатеринбург")
        );
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        Optional<Candidate> candidateOptional = Optional.of(candidate);
        Model model = mock(Model.class);
        CandidateService candidateService = mock(CandidateService.class);
        CityService cityService = mock(CityService.class);
        CandidateController candidateController = new CandidateController(candidateService, cityService);
        when(candidateService.findById(0)).thenReturn(candidateOptional);
        when(cityService.findAll()).thenReturn(cityCollection);

        String page = candidateController.getById(model, 0);
        verify(model).addAttribute("cities", cityCollection);
        verify(model).addAttribute("candidate", candidate);
        assertThat(page, is("candidates/edit"));

    }

    @Test
    public void delete() {
        Candidate candidate = new Candidate(0, "Tom", "descriptionTom", true, 1);
        Model model = mock(Model.class);
        CandidateService candidateService = mock(CandidateService.class);
        CityService cityService = mock(CityService.class);
        CandidateController candidateController = new CandidateController(candidateService, cityService);
        when(candidateService.deleteById(0)).thenReturn(true);

        String page = candidateController.delete(model, 0);
        assertThat(page, is("redirect:/candidates"));

    }
}