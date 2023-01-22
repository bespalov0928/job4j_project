package ru.job4j.job4j_accident.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.job4j_accident.model.Accident;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model) {
        List<Accident> accidents = new ArrayList<>();
        accidents.add(new Accident(1, "name1", "text1", "address1"));
        accidents.add(new Accident(2, "name2", "text2", "address2"));
        accidents.add(new Accident(3, "name3", "text3", "address3"));
        model.addAttribute("user", "Petr Arsentev");
        model.addAttribute("accidents", accidents);
        return "index";
    }
}
