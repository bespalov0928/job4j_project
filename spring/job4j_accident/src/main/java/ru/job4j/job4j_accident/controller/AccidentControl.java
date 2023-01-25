package ru.job4j.job4j_accident.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.service.SimpleAccidentService;
import ru.job4j.job4j_accident.service.SimpleAccidentTypeService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentControl {
    private final SimpleAccidentService accidentService;
    private final SimpleAccidentTypeService accidentTypeService;

    @GetMapping("/formCreateAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = (List<AccidentType>) accidentTypeService.findAll();
        model.addAttribute("types", types);
        return "accident/createAccident";
    }

    @PostMapping("/createAcc")
    public String save(@ModelAttribute Accident accident) {
//        Optional<AccidentType> accidentType = accidentTypeService.findById(accident.getType().getId());
//        accident.setType(accidentType.get());
        accidentService.save(accident);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        List<AccidentType> types = (List<AccidentType>) accidentTypeService.findAll();
        Optional<Accident> rsl = accidentService.findById(id);
        model.addAttribute("accident", rsl.get());
        model.addAttribute("types", types);
        model.addAttribute("typeId", rsl.get().getType().getId());
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
