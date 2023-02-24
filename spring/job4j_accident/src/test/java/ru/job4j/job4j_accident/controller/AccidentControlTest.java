package ru.job4j.job4j_accident.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.job4j_accident.Main;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.service.SimpleAccidentService;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControlTest {

    private final Accident accident = new Accident(1, "name", "text", "adress", new AccidentType(), new ArrayList<>());

    @MockBean
    private SimpleAccidentService accidentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void viewCreateAccident() throws Exception {
        this.mockMvc.perform(get("/accidents/formCreateAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"));
    }

    @Test
    @WithMockUser
    public void getById() throws Exception {
        var ids = new String[]{"1"};
        accidentService.save(accident, ids);
        when(accidentService.findById(accident.getId())).thenReturn(Optional.of(accident));
        this.mockMvc.perform(get("/accidents/formUpdateAccident?id=1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
    }

    @WithMockUser
    @Test
    public void save() throws Exception {
        String[] ids = new String[]{"1"};

        this.mockMvc.perform(post("/accidents/createAcc")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("rids", ids))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture(), eq(ids));
        assertThat(argument.getValue().getName()).isEqualTo(accident.getName());
    }

    @WithMockUser
    @Test
    public void update() throws Exception {
        String[] ids = new String[]{"1"};
        when(accidentService.update(accident, ids)).thenReturn(true);

        this.mockMvc.perform(multipart("/accidents/update")
                        .param("id", String.valueOf(accident.getId()))
                        .param("name", "name1")
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("rids", ids))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
        ArgumentCaptor<Accident> argumentSave = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argumentSave.capture(), eq(ids));
        assertThat(argumentSave.getValue().getName()).isEqualTo("name1");
    }


}