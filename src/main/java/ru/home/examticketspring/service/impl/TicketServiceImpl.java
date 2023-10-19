package ru.home.examticketspring.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.repository.SpringTicketsRepository;
import ru.home.examticketspring.service.TicketService;
import ru.home.examticketspring.model.SpringTicket;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final SpringTicketsRepository springTicketsRepository;

    @CacheEvict(value = "rowCountCache", allEntries = true)
    @Override
    public long count() {
        return springTicketsRepository.count();
    }

    @Override
    public Optional<SpringTicket> findById(long id) {
        return springTicketsRepository.findById(id);
    }
}