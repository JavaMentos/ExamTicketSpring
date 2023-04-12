package ru.home.examticketspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.examticketspring.model.ExamTicket;

public interface ExamTicketRepository extends JpaRepository<ExamTicket, Long> {
}
