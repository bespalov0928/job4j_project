package ru.job4j.dreamjob.controller;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.UserService;
import ru.job4j.dreamjob.service.VacancyService;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void getAll() {
        Collection<User> users = Arrays.asList(
                new User(0, "email", "password"),
                new User(1, "email1", "password1")
        );
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        when(userService.findAll()).thenReturn(users);
        UserController userController = new UserController(userService);

        String page = userController.getAll(model);
        verify(model).addAttribute("users", users);
        assertThat(page, is("users/list"));

    }

    @Test
    public void getCreatePage() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        String page = userController.getCreatePage();
        assertThat(page, is("users/create"));
    }

    @Test
    public void registration() {
        Model model = mock(Model.class);
        User user = new User(0, "email", "password");
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.save(user)).thenReturn(Optional.of(user));

        String page = userController.registration(model, user);
        assertThat(page, is("redirect:/users"));
    }

    @Test
    public void getById() {
        Model model = mock(Model.class);
        User user = new User(0, "email", "password");
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.findById(0)).thenReturn(Optional.of(user));

        String page = userController.getById(model, 0);
        assertThat(page, is("users/edit"));
    }

    @Test
    public void update() {
        Model model = mock(Model.class);
        User user = new User(0, "email", "password");
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.update(user)).thenReturn(true);

        String page = userController.update(user, model);
        assertThat(page, is("redirect:/users"));
    }

    @Test
    public void delete() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.deleteById(0)).thenReturn(true);

        String page = userController.delete(model, 0);
        assertThat(page, is("redirect:/users"));
    }

    @Test
    public void loginPage() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);

        String page = userController.loginPage(model, false);
        assertThat(page, is("login"));
    }

    @Test
    public void login() {
        User user = new User(0, "email", "password");
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.findUserByEmailAndPassword("email", "password")).thenReturn(Optional.of(user));

        String page = userController.login(user);
        assertThat(page, is("redirect:/index"));
    }
}