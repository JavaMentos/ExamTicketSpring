package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

/**
 * Интерфейс ProcessingMessageService представляет сервис для обработки сообщений.
 */
public interface ProcessingMessageService {

    /**
     * Метод getRandomTicket() используется для получения случайного тикета.
     *
     * @return Объект SpringTicket, представляющий случайный тикет.
     */
    SpringTicket getRandomTicket();

    /**
     * Метод formatExamTicket() используется для форматирования информации о тикете.
     *
     * @param springTicket Объект SpringTicket, содержащий информацию о тикете.
     * @return Строка, отформатированная в соответствии с требованиями для представления тикета.
     */
    String formatExamTicket(SpringTicket springTicket);
}
