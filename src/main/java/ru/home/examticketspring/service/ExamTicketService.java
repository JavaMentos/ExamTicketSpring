package ru.home.examticketspring.service;

import ru.home.examticketspring.model.ExamTicket;

import java.util.Optional;

public interface ExamTicketService {
    long count();
    Optional<ExamTicket> findById(Long id);
}