package ru.home.examticketspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Класс ExamTicketSpringApplication представляет точку входа в приложение.
 * Он использует аннотации Spring Boot для настройки и запуска приложения.
 */
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class ExamTicketSpringApplication {
    /**
     * Метод main является точкой входа в приложение.
     *
     * @param args массив строковых аргументов командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(ExamTicketSpringApplication.class, args);
    }
}