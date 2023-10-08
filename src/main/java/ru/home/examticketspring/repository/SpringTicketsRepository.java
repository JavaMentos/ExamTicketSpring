package ru.home.examticketspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.examticketspring.model.SpringTicket;

@Repository
public interface SpringTicketsRepository extends JpaRepository<SpringTicket, Long> {
}
