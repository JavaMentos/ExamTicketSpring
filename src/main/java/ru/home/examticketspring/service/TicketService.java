package ru.home.examticketspring.service;

import ru.home.examticketspring.model.SpringTicket;

import java.util.Optional;

public interface TicketService {
    long count();
    Optional<SpringTicket> findById(long id);
}