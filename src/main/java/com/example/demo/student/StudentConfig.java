package com.example.demo.student;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student("Mariam", "Mariam@gmail.com", LocalDate.of(1995, Month.JANUARY, 5));
            Student alex = new Student("Alex", "Alex@gmail.com", LocalDate.of(1999, Month.MARCH, 21));
            repository.saveAll(List.of(mariam,alex));
        };
    }
}
