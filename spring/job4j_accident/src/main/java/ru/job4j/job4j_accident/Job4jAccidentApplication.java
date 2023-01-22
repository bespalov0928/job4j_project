package ru.job4j.job4j_accident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Job4jAccidentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ru.job4j.job4j_accident.Job4jAccidentApplication.class, args);
        System.out.println("Go to http://localhost:8080/index");
    }

}
