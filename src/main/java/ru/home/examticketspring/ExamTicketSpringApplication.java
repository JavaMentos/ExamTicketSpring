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

//TODO Релизовать вытаскивания вопроса по номеру, сначала делается запрос к бд, на количество строчек.
//TODO Отредактировать вопросы. Должно быть номер вопроса, тема вопроса, сам вопрос.