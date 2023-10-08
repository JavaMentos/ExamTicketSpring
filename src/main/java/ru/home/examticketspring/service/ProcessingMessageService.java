package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

public interface ProcessingMessageService {
    SpringTicket getRandomTicket();
    String formatExamTicket(SpringTicket springTicket);
}
