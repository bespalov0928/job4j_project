package ru.job4j.job4j_auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.job4j_auth.Main;
import ru.job4j.job4j_auth.model.Person;
import ru.job4j.job4j_auth.service.SimplePersonService;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PersonControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SimplePersonService personService;

    @Autowired
    private MockMvc mockMvc;

    private final Person person1 = new Person(1, "login1", "password1");
    private final Person person2 = new Person(2, "login2", "password2");

    @Test
    @WithMockUser
    public void findAll() throws Exception {
        Person personMock1 = personService.save(person1);
        Person personMock2 = personService.save(person2);
        when(personService.findAll()).thenReturn(Arrays.asList(person1, person2));
        this.mockMvc.perform(
                        get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(person1, person2))));
    }

    @Test
    @WithMockUser
    void findById() throws Exception {
        when(personService.findById(1)).thenReturn(Optional.of(person1));
        this.mockMvc.perform(
                        get("/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("login1"));
    }

    @Test
    @WithMockUser
    void create() throws Exception {
        when(personService.save(person1)).thenReturn(person1);
        this.mockMvc.perform(
                        post("/person/")
                                .content(objectMapper.writeValueAsString(person1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value("login1"));
    }

    @Test
    @WithMockUser
    void update() throws Exception {
        when(personService.update(person1)).thenReturn(true);
        this.mockMvc.perform(
                        put("/person/")
                                .content(objectMapper.writeValueAsString(person1))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    void whenDelete() throws Exception {
        when(personService.delete(1)).thenReturn(true);
        this.mockMvc.perform(
                        delete("/person/1"))
                .andExpect(status().isOk());
    }

}