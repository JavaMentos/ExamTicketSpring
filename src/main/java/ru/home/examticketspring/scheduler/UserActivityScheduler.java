package ru.home.examticketspring.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.UserService;
import ru.home.examticketspring.utls.TelegramUserContainer;

import java.util.Map;
import java.util.Optional;

/**
 * Класс UserActivityScheduler представляет сервис для планирования и
 * обновления активности пользователей в базе данных.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserActivityScheduler {
    private final UserService userService;
    private static final int DEFAULT_COUNTER = 1;

    /**
     * Метод updateUserActivityInDB() вызывается по расписанию для обновления активности пользователей в базе данных.
     */
    @Scheduled(initialDelayString = "${scheduling.initialDelay}", fixedDelayString = "${scheduling.fixedDelay}")
    public void updateUserActivityInDB() {
        log.info("Планировщик начал работу");
        for (Map.Entry<Long, TelegramUser> entry : TelegramUserContainer.statisticsUser.entrySet()) {
            Long chatId = entry.getKey();
            TelegramUser userFromMap = entry.getValue();

            updateActivityForUser(chatId, userFromMap);
        }
    }

    private void updateActivityForUser(Long chatId, TelegramUser userFromMap) {
        Optional<TelegramUser> userFromDb = userService.findByUserId(chatId);

        if (userFromDb.isEmpty()) {
            log.error("Пользователь не найден в базе данных: " + userFromDb);
            return;
        }

        if (shouldUpdateLastActiveDate(userFromDb.get(), userFromMap)) {
            userService.updateLastActiveDate(chatId, userFromMap.getLastActiveDate());
            userService.updateCounter(chatId, DEFAULT_COUNTER);
            log.info("Обновлена дата активности пользователя: " + userFromDb);
        }

        if (shouldUpdateCounter(userFromDb.get(), userFromMap)) {
            userService.updateCounter(chatId, userFromDb.get().getCounter());
            log.info("Обновлена активность пользователя: " + userFromDb);
        }
    }

    private boolean shouldUpdateLastActiveDate(TelegramUser userFromDb, TelegramUser userFromMap) {
        return !userFromDb.getLastActiveDate().isEqual(userFromMap.getLastActiveDate());
    }

    private boolean shouldUpdateCounter(TelegramUser userFromDb, TelegramUser userFromMap) {
        return userFromDb.getCounter() < userFromMap.getCounter();
    }

}
