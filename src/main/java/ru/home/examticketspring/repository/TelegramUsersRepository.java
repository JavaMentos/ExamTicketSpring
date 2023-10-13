package ru.home.examticketspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.home.examticketspring.model.TelegramUser;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Интерфейс TelegramUsersRepository представляет репозиторий
 * для доступа к данным о пользователе Telegram в базе данных.
 */
@Transactional
@Repository
public interface TelegramUsersRepository extends JpaRepository<TelegramUser, Long> {
    @Modifying
    @Query("update TelegramUser t set t.lastActiveDate = :lastActiveDate where t.userId = :chatId")
    void updateLastActiveDateByUserId(@Param("chatId") Long chatId, @Param("lastActiveDate") LocalDate lastActiveDate);

    @Modifying
    @Query("update TelegramUser t set t.counter = :counter where t.userId = :chatId")
    void updateCounterByUserId(@Param("chatId") Long chatId, @Param("counter") Integer counter);

    Optional<TelegramUser> findByUserId(long userId);
}