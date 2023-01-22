package ru.job4j.job4j_accident.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.service.SimpleAccidentService;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentControl {
    private final SimpleAccidentService accidentService;

    @GetMapping("/createAcc")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/createAcc")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var rsl = accidentService.findById(id);
        model.addAttribute("accident", rsl.get());
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model) {
        boolean rsl = accidentService.update(accident);
        if (!rsl) {
            model.addAttribute("message", "Инцидент не найден");
            return "errors/404";
        }
        return "redirect:/index";
    }
}
