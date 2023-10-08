package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

/**
 * Интерфейс TelegramService представляет сервис для взаимодействия с Telegram API.
 */
public interface TelegramService {
    /**
     * Метод sendTextMessage() используется для отправки текстового сообщения в чат.
     *
     * @param text   Текст сообщения.
     * @param chatId Идентификатор чата, в который будет отправлено сообщение.
     */
    void sendTextMessage(String text, String chatId);
    /**
     * Метод sendQuizPoll() используется для отправки викторины (опроса) в чат.
     *
     * @param springTicket Объект SpringTicket, представляющий тикет для викторины.
     * @param chatId       Идентификатор чата, в который будет отправлена викторина.
     */
    void sendQuizPoll(SpringTicket springTicket, String chatId);
    /**
     * Метод createWelcomeMessage() используется для создания приветственного сообщения.
     *
     * @return Строка, содержащая приветственное сообщение.
     */
    String createWelcomeMessage();
}
