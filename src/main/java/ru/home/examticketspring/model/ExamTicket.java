package ru.home.examticketspring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ExamTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String theme;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private Integer correctAnswer;

    // Геттеры и сеттеры
}