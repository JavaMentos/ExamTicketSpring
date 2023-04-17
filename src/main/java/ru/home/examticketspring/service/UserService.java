package ru.home.examticketspring.service;

import ru.home.examticketspring.model.TelegramUser;

import java.util.List;

public interface UserService {
    void addUser(TelegramUser telegramUser);
    List<TelegramUser> getAllUsers();
    List<Long> getAllUserId();
}
