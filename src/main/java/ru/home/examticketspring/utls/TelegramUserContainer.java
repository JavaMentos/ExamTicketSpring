package ru.home.examticketspring.utls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.examticketspring.model.TelegramUser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс TelegramUserContainer является контейнером для хранения статистики пользователей Telegram.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TelegramUserContainer {
    private static final Map<Long, TelegramUser> localContainerUsers = new ConcurrentHashMap<>();

    /**
     * Получает список всех пользователей Telegram в контейнере.
     *
     * @return Список всех пользователей Telegram.
     */
    public static List<TelegramUser> getAllUsers() {
        return new ArrayList<>(localContainerUsers.values());
    }

    /**
     * Добавляет пользователя Telegram в контейнер.
     * Если пользователь уже существует, его данные будут перезаписаны.
     *
     * @param user Пользователь Telegram, который должен быть добавлен.
     */
    public static void addUser(TelegramUser user) {
        localContainerUsers.put(user.getUserId(), user);
    }

    /**
     * Метод updateUserActivity() используется для обновления активности пользователя.
     * Обновляет дату последней активности и увеличивает счетчик активностей на 1.
     *
     * @param chatId Идентификатор чата пользователя.
     */
    public static void updateUserActivity(Long chatId) {
        TelegramUser user = localContainerUsers.get(chatId);
        LocalDate today = LocalDate.now();
        if (!user.getLastActiveDate().isEqual(today)) {
            user.setLastActiveDate(today);
            user.setCounter(1);
            return;
        }
            user.setCounter(user.getCounter() + 1);
    }

    /**
     * Метод initializeStaticsMap() используется для инициализации статической карты statisticsUser.
     * Заполняет карту значениями из переданного списка всех пользователей.
     *
     * @param allUsers Список всех пользователей.
     */
    public static void initializeStaticsMap(List<TelegramUser> allUsers) {
        allUsers.forEach(user -> localContainerUsers.put(user.getUserId(), user));
    }

    /**
     * Метод isNewUsers() используется для проверки, является ли пользователь новым.
     *
     * @param userId Идентификатор пользователя.
     * @return true, если пользователь новый; false, если пользователь уже существует в контейнере статистики.
     */
    public static boolean isNewUsers(Long userId) {
        return !localContainerUsers.containsKey(userId);
    }
}
