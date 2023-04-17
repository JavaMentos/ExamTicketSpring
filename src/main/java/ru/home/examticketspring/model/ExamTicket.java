package ru.home.examticketspring.model;

import javax.persistence.*;

@Entity
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

    public long getId() {
        return id;
    }

    public String getQuestionTopic() {
        return QuestionTopic;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getFullAnswer() {
        return fullAnswer;
    }
}