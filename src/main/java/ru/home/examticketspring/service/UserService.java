package ru.home.examticketspring.service;

import ru.home.examticketspring.model.TelegramUser;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    void addUser(TelegramUser telegramUser);
    List<TelegramUser> getAllUsers();
    List<Long> getAllUserId();
    void updateLastActiveDate(long chatId, LocalDate lastActiveDate);
    void updateCounter(long chatId, Integer counter);
    TelegramUser findByUserId(long userId);
}
