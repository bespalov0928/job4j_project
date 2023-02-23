package ru.job4j.job4j_accident.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.job4j_accident.model.User;
import ru.job4j.job4j_accident.repository.jpa.AuthorityRepository;
import ru.job4j.job4j_accident.repository.jpa.UserRepository;

@Controller
public class RegControl {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public RegControl(PasswordEncoder encoder, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorityRepository.findByAuthority("ROLE_USER"));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
