package ru.home.examticketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ExamTicketSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamTicketSpringApplication.class, args);
    }
}

//TODO добавить возможность вытаскивать по конкретные ответы по номеру вопроса или строки
//TODO разобраться с логирование, начать обрабатывать ошибки
//TODO разобраться с исключения, кроме логирования, нужно еще отправлять сообщение в чат