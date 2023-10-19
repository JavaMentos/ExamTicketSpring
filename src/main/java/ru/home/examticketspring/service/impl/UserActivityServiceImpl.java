package ru.home.examticketspring.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.UserActivityService;
import ru.home.examticketspring.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserActivityServiceImpl implements UserActivityService {
    private final UserService userService;

    @Override
    public void updateUserActivity(List<TelegramUser> users) {
        for (TelegramUser userFromMap : users) {
            updateActivityForUser(userFromMap);
        }
    }

    private void updateActivityForUser(TelegramUser userFromMap) {
        Long userId = userFromMap.getUserId();
        Optional<TelegramUser> userFromDb = userService.findByUserId(userId);

        if (userFromDb.isEmpty()) {
            log.error("Пользователь не найден в базе данных: {}", userFromMap);
            return;
        }

        if (shouldUpdateLastActiveDate(userFromDb.get(), userFromMap)) {
            userService.updateLastActiveDate(userId, userFromMap.getLastActiveDate());
            userService.updateCounter(userId, userFromMap.getCounter());
            log.info("Обновлена дата активности пользователя: {}", userFromMap);
        }

        if (shouldUpdateCounter(userFromDb.get(), userFromMap)) {
            userService.updateCounter(userId, userFromMap.getCounter());
            log.info("Обновлена активность пользователя: {}", userFromMap);
        }
    }

    private boolean shouldUpdateLastActiveDate(TelegramUser userFromDb, TelegramUser userFromMap) {
        return !userFromDb.getLastActiveDate().isEqual(userFromMap.getLastActiveDate());
    }

    private boolean shouldUpdateCounter(TelegramUser userFromDb, TelegramUser userFromMap) {
        return userFromDb.getCounter() < userFromMap.getCounter();
    }

}
