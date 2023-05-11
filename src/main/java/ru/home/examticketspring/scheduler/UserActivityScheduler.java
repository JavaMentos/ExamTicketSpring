package ru.home.examticketspring.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.impl.handler.IncomingMessageImpl;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.UserService;

import java.util.Map;

@Service
public class UserActivityScheduler {
    private final UserService userService;
    private final int DEFAULT_COUNTER = 1;

    public UserActivityScheduler(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(initialDelayString = "${scheduling.initialDelay}", fixedDelayString = "${scheduling.fixedDelay}")
    public void updateUserActivityInDB() {
        for (Map.Entry<Long, TelegramUser> entry : IncomingMessageImpl.statisticsUser.entrySet()) {
            Long chatId = entry.getKey();
            TelegramUser userFromMap = entry.getValue();

            // Получаем пользователя из БД
            TelegramUser userFromDb = userService.findByUserId(chatId);

            if (userFromDb == null)
                throw new IllegalArgumentException("Данный пользователь - " + chatId + " не найден ");

            // Сравниваем даты, если в бд дата старая, то сбасываем счетчик и обновляем дату
            if (!userFromDb.getLastActiveDate().isEqual(userFromMap.getLastActiveDate())) {
                userService.updateLastActiveDate(chatId, userFromMap.getLastActiveDate());
                userFromMap.setCounter(DEFAULT_COUNTER);
                userService.updateCounter(chatId, DEFAULT_COUNTER);
            }

            // Сравниваем счетчики
            if (userFromDb.getCounter() < userFromMap.getCounter()) {
                userService.updateCounter(chatId, userFromMap.getCounter());
            }
        }
    }
}
