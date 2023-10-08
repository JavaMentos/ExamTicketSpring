package ru.home.examticketspring.utls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.home.examticketspring.model.TelegramUser;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TelegramUserContainer {
    public static final Map<Long, TelegramUser> statisticsUser = new ConcurrentHashMap<>();

    public static void updateUserActivity(Long chatId) {
        TelegramUser user = statisticsUser.get(chatId);
        LocalDate today = LocalDate.now();
        if (!user.getLastActiveDate().isEqual(today)) {
            user.setLastActiveDate(today);
        }
        user.setCounter(user.getCounter() + 1);
    }

    public static void initializeStaticsMap(List<TelegramUser> allUsers) {
        allUsers.forEach(user -> statisticsUser.put(user.getUserId(), user));
    }

    public static boolean isNewUsers(Long userId) {
        return statisticsUser.containsKey(userId);
    }
}
