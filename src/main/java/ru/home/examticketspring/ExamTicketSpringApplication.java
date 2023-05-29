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

//    @Bean
//    public TomcatServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
}

//TODO начать писать документирование