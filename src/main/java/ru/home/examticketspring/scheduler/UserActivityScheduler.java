package ru.home.examticketspring.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.UserService;
import ru.home.examticketspring.utls.TelegramUserContainer;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserActivityScheduler {
    private final UserService userService;
    private static final int DEFAULT_COUNTER = 1;

    @Scheduled(initialDelayString = "${scheduling.initialDelay}", fixedDelayString = "${scheduling.fixedDelay}")
    public void updateUserActivityInDB() {
        log.info("Планировщик начал работу");
        for (Map.Entry<Long, TelegramUser> entry : TelegramUserContainer.statisticsUser.entrySet()) {
            Long chatId = entry.getKey();
            TelegramUser userFromMap = entry.getValue();

            // Получаем пользователя из БД
            TelegramUser userFromDb = userService.findByUserId(chatId);

            if (userFromDb == null) {
                log.error("Пользователь не найден в базе данных " + userFromDb);
                continue;
            }
            // Сравниваем даты, если в бд дата старая, то сбасываем счетчик и обновляем дату
            if (!userFromDb.getLastActiveDate().isEqual(userFromMap.getLastActiveDate())) {
                userService.updateLastActiveDate(chatId, userFromMap.getLastActiveDate());
                userFromMap.setCounter(DEFAULT_COUNTER);
                userService.updateCounter(chatId, DEFAULT_COUNTER);
                log.info("Обновилена дата активности пользователя " + userFromDb);
            }

            // Сравниваем счетчики
            if (userFromDb.getCounter() < userFromMap.getCounter()) {
                userService.updateCounter(chatId, userFromMap.getCounter());
                log.info("Обновилена активность пользователя " + userFromDb);
            }
        }
    }
}
