package ru.home.examticketspring.service;

import ru.home.examticketspring.model.TelegramUser;

import java.util.List;

/**
 * Интерфейс UserActivityService предоставляет контракт для обновления активности пользователей.
 */
public interface UserActivityService {

    /**
     * Обновляет активность пользователей на основе предоставленного списка.
     *
     * @param users Список пользователей Telegram, которых необходимо обновить.
     */
    void updateUserActivity(List<TelegramUser> users);
}