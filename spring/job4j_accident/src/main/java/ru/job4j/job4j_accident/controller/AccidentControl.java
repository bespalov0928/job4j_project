package ru.job4j.job4j_accident.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_accident.model.Accident;
import ru.job4j.job4j_accident.model.AccidentType;
import ru.job4j.job4j_accident.model.Rule;
import ru.job4j.job4j_accident.service.SimpleAccidentService;
import ru.job4j.job4j_accident.service.SimpleAccidentTypeService;
import ru.job4j.job4j_accident.service.SimpleRuleService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentControl {
    private final SimpleAccidentService accidentService;
    private final SimpleAccidentTypeService accidentTypeService;
    private final SimpleRuleService ruleService;

    @GetMapping("/formCreateAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypeService.findAll());
        model.addAttribute("rules", ruleService.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/createAcc")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rids");
        accidentService.save(accident, ids);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        List<AccidentType> typeList = (List<AccidentType>) accidentTypeService.findAll();
        List<Rule> ruleList = (List<Rule>) ruleService.findAll();
        Optional<Accident> accident = accidentService.findById(id);
//        List<Rule> rules = new ArrayList<>();
        List<Rule> rules = accident.get().getRules();
        Optional<AccidentType> accidentType = Optional.ofNullable(accident.get().getType());

        model.addAttribute("accident", accident.get());
        model.addAttribute("types", typeList);
        model.addAttribute("typeId", accidentType.isEmpty() ? 0 : accidentType.get().getId());
        model.addAttribute("rules", ruleList);
        model.addAttribute("rulesId", rules == null ? new ArrayList<>() : rules);
        return "accident/editAccident";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rids");
        boolean rsl = accidentService.update(accident, ids);
        if (!rsl) {
            model.addAttribute("message", "Инцидент не найден");
            return "errors/404";
        }
        return "redirect:/index";
    }
}
