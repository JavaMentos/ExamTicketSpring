package ru.home.examticketspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.examticketspring.model.SpringTicket;

/**
 * Интерфейс SpringTicketsRepository представляет репозиторий
 * для доступа к данным о тикетах в базе данных.
 */
@Repository
public interface SpringTicketsRepository extends JpaRepository<SpringTicket, Long> {
}
