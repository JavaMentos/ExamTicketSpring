package ru.home.examticketspring.service.impl;

import org.springframework.cache.annotation.CacheEvict;
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

    @CacheEvict(value = "rowCountCache", allEntries = true)
    @Override
    public long count() {
        return examTicketRepository.count();
    }

    @Override
    public Optional<ExamTicket> findById(long id) {
        return examTicketRepository.findById(id);
    }
}