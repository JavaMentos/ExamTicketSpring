package ru.home.examticketspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.examticketspring.model.TelegramUser;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
}
