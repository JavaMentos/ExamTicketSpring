package ru.home.examticketspring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.repository.TelegramUserRepository;
import ru.home.examticketspring.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Override
    public void addUser(TelegramUser telegramUser) {
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public List<TelegramUser> getAllUsers() {
        return telegramUserRepository.findAll();
    }

    @Override
    public List<Long> getAllUserId() {
        List<TelegramUser> allUsers = getAllUsers();
        return allUsers.stream().map(TelegramUser::getUserId).collect(Collectors.toList());
    }
}
