package ru.home.examticketspring.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * Класс SpringTicket представляет сущность для хранения информации о тикете в базе данных.
 */
@Entity
@Getter
@Table(name = "spring_tickets")
public class SpringTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "question_topic", length = 100)
    private String questionTopic;
    @Column(name = "question", length = 150)
    private String question;
    @Column(name = "answer_1", length = 100)
    private String answer1;
    @Column(name = "answer_2", length = 100)
    private String answer2;
    @Column(name = "answer_3", length = 100)
    private String answer3;
    @Column(name = "answer_4", length = 100)
    private String answer4;
    @Column(name = "full_answer", length = 2500)
    private String fullAnswer;
}