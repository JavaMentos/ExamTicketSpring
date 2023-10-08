package ru.home.examticketspring.service;

import ru.home.examticketspring.model.TelegramUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс UserService представляет сервис для работы с пользователями Telegram.
 */
public interface UserService {
    /**
     * Метод addUser() используется для добавления нового пользователя в систему.
     *
     * @param telegramUser Объект TelegramUser, представляющий пользователя Telegram.
     */
    void addUser(TelegramUser telegramUser);
    /**
     * Метод getAllUsers() используется для получения списка всех пользователей.
     *
     * @return Список объектов TelegramUser, представляющих всех пользователей.
     */
    List<TelegramUser> getAllUsers();
    /**
     * Метод getAllUserId() используется для получения списка идентификаторов всех пользователей.
     *
     * @return Список идентификаторов (тип Long) всех пользователей.
     */
    List<Long> getAllUserId();
    /**
     * Метод updateLastActiveDate() используется для обновления даты последней активности пользователя.
     *
     * @param chatId          Идентификатор чата пользователя.
     * @param lastActiveDate  Дата последней активности.
     */
    void updateLastActiveDate(long chatId, LocalDate lastActiveDate);
    /**
     * Метод updateCounter() используется для обновления значения счетчика пользователя.
     *
     * @param chatId          Идентификатор чата пользователя.
     * @param counter         Новое значение счетчика.
     */
    void updateCounter(long chatId, Integer counter);
    /**
     * Метод findByUserId() используется для поиска пользователя по его идентификатору.
     *
     * @param userId Идентификатор пользователя.
     * @return Объект Optional, содержащий найденного пользователя или пустое значение, если пользователь не найден.
     */
    Optional<TelegramUser> findByUserId(long userId);
}
