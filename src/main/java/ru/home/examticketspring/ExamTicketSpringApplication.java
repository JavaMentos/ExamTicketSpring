package ru.home.examticketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ExamTicketSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamTicketSpringApplication.class, args);
    }
}
