package ru.job4j.dreamjob.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    @GetMapping("/registration")
    public String getCreatePage() {
        return "users/create";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> userFind = userService.save(user);
//        Optional<User> userFind = userService.findUserByEmail(user.getEmail());
        if (userFind.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "errors/404";
//            return "redirect:/fail";
        }
        return "redirect:/users";
//        return "redirect:/success";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            model.addAttribute("message", "Пользователь по идентификатору не найден");
            return "errors/404";
        }
        model.addAttribute("user", userOptional.get());
        return "users/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute User user, Model model) {
        var isUpdated = userService.update(user);
        if (!isUpdated) {
            model.addAttribute("message", "Пользователь по идентификатору не найден");
            return "errors/404";
        }
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        var isDeleted = userService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Пользователь по идентификатору не найден");
            return "errors/404";
        }
        return "redirect:/users";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        return "redirect:/index";
    }

}
