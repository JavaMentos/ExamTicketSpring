package ru.home.examticketspring.service;

import ru.home.examticketspring.model.ExamTicket;

public interface ProcessingMessageService {
    ExamTicket getRandomTicket();
    String formatExamTicket(ExamTicket examTicket);
}
