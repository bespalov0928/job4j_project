package ru.job4j.job4j_accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.service.AccidentService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    private final AccidentService accidentService;

    public IndexController(AccidentService accidentService) {
        this.accidentService = accidentService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}
