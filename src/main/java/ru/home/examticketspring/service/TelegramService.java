package ru.home.examticketspring.service;

import ru.home.examticketspring.model.ExamTicket;

public interface TelegramService {
    void sendTextMessage(String text, String chatId);
    void sendQuizPoll(ExamTicket examTicket, String chatId);
    String createWelcomeMessage();
}
