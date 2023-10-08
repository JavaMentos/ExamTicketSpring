package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

import java.util.Optional;

/**
 * Интерфейс TicketService представляет сервис для работы с тикетами.
 */
public interface TicketService {
    /**
     * Метод count() возвращает количество доступных тикетов.
     *
     * @return Количество доступных тикетов.
     */
    long count();
    /**
     * Метод findById() используется для поиска тикета по его идентификатору.
     *
     * @param id Идентификатор тикета.
     * @return Объект Optional, содержащий найденный тикет или пустое значение, если тикет не найден.
     */
    Optional<SpringTicket> findById(long id);
}