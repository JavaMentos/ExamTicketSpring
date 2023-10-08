package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

public interface TelegramService {
    void sendTextMessage(String text, String chatId);
    void sendQuizPoll(SpringTicket springTicket, String chatId);
    String createWelcomeMessage();
}
