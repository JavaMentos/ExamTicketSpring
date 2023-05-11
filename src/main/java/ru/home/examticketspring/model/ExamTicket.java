package ru.home.examticketspring.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "exam_tickets")
public class ExamTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "question_topic")
    private String QuestionTopic;
    @Column(name = "question")
    private String question;
    @Column(name = "answer_1")
    private String answer1;
    @Column(name = "answer_2")
    private String answer2;
    @Column(name = "answer_3")
    private String answer3;
    @Column(name = "answer_4")
    private String answer4;
    @Column(name = "right_answer")
    private String rightAnswer;
    @Column(name = "full_answer")
    private String fullAnswer;
    @Column(name = "correct_answer")
    private int correctAnswer;
}