package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Controller
public class IndexController {
    @GetMapping("/index")
    public String getIndex() {
//        return "Hello world";
        return "index";
    }

}
