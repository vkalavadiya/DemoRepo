package com.example.demo2.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository repository) {
        return args -> {
            Student abc = new Student(
                 "abc",
                 "abc@gmail.com",
                    21
            );
            Student xyz = new Student(
                    "xyz",
                    "xyz@gmail.com",
                    20
            );

            repository.saveAll(
                    List.of(abc, xyz)
            );

        };
    }
}
