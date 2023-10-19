package ru.home.examticketspring.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.service.UserActivityService;
import ru.home.examticketspring.utls.TelegramUserContainer;

/**
 * Класс UserActivityScheduler представляет сервис для планирования и
 * обновления активности пользователей в базе данных.
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class UserActivityScheduler {

    private final UserActivityService userActivityService;
    /**
     * Метод updateUserActivityInDB() вызывается по расписанию для обновления активности пользователей в базе данных.
     */
    @Scheduled(initialDelayString = "${scheduling.initialDelay}", fixedDelayString = "${scheduling.fixedDelay}")
    public void updateUserActivityInDB() {
        log.info("Планировщик начал работу");
        userActivityService.updateUserActivity(TelegramUserContainer.getAllUsers());
    }
}
