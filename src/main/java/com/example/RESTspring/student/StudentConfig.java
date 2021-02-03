package com.example.RESTspring.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner( StudentRepository repository){
        return args -> {
            Student ritesh = new Student(
                    "ritesh",
                    "Riteshkhadse12@gmail.com",
                    LocalDate.of(2000, Month.APRIL,17)
            );

            Student aysuh = new Student(
                    "Ayush Khadse",
                    "ayushg12@gmail.com",
                    LocalDate.of(2008, Month.MAY,10)
            );
            repository.saveAll(List.of(ritesh,aysuh));
        };
    }
}
