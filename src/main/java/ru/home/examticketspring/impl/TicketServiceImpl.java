package ru.home.examticketspring.impl;

import org.springframework.stereotype.Service;
import ru.home.examticketspring.repository.ExamTicketRepository;
import ru.home.examticketspring.service.TicketService;
import ru.home.examticketspring.model.ExamTicket;

import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final ExamTicketRepository examTicketRepository;

    public TicketServiceImpl(ExamTicketRepository examTicketRepository) {
        this.examTicketRepository = examTicketRepository;
    }

    @Override
    public long count() {
        return examTicketRepository.count();
    }

    @Override
    public Optional<ExamTicket> findById(long id) {
        return examTicketRepository.findById(id);
    }
}