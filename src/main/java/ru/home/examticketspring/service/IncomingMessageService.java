package ru.home.examticketspring.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IncomingMessageService {
    void handlerMessage(Update update);
}
