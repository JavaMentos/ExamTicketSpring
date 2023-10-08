package ru.home.examticketspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.repository.TelegramUsersRepository;
import ru.home.examticketspring.service.UserService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final TelegramUsersRepository telegramUsersRepository;

    @Override
    public void addUser(TelegramUser telegramUser) {
        telegramUsersRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> getAllUsers() {
        return telegramUsersRepository.findAll();
    }

    @Override
    public List<Long> getAllUserId() {
        List<TelegramUser> allUsers = getAllUsers();
        return allUsers.stream().map(TelegramUser::getUserId).toList();
    }

    @Override
    public void updateLastActiveDate(long chatId, LocalDate lastActiveDate) {
        telegramUsersRepository.updateLastActiveDate(chatId, lastActiveDate);
    }

    @Override
    public void updateCounter(long chatId, Integer counter) {
        telegramUsersRepository.updateCounter(chatId, counter);
    }

    @Override
    public TelegramUser findByUserId(long userId) {
        return telegramUsersRepository.findByUserId(userId);
    }
}