package ru.home.examticketspring.service;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Интерфейс IncomingMessageService представляет сервис для обработки входящих сообщений.
 */
public interface IncomingMessageService {
    /**
     * Метод handlerMessage() используется для обработки входящего сообщения.
     *
     * @param update Объект Update, содержащий информацию о входящем сообщении.
     */
    void handlerMessage(Update update);
}
