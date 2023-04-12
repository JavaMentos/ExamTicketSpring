package ru.home.examticketspring.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.repository.ExamTicketRepository;
import ru.home.examticketspring.service.ExamTicketService;
import ru.home.examticketspring.model.ExamTicket;

import java.util.Optional;


@Service
public class ExamTicketServiceImpl implements ExamTicketService {

    @Autowired
    private ExamTicketRepository examTicketRepository;

    @Override
    public long count() {
        return examTicketRepository.count();
    }

    @Override
    public Optional<ExamTicket> findById(Long id) {
        return examTicketRepository.findById(id);
    }
}