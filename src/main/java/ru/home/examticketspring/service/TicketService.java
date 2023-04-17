package ru.home.examticketspring.service;

import ru.home.examticketspring.model.ExamTicket;

import java.util.Optional;

public interface TicketService {
    long count();
    Optional<ExamTicket> findById(long id);
}