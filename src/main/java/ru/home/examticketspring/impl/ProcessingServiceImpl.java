package ru.home.examticketspring.impl;

import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingService;
import ru.home.examticketspring.service.TicketService;

import java.util.Optional;
import java.util.Random;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    private final TicketService ticketService;

    public ProcessingServiceImpl(TicketService ticketService){
        this.ticketService = ticketService;
    }

    public ExamTicket getRandomTicket() {
        int numberLine = randomNumber();
        Optional<ExamTicket> examTicket = ticketService.findById(numberLine);
        if (examTicket.isPresent()) return examTicket.get();
        else throw new IllegalArgumentException("По строке не удалось найди запись в БД");
    }

    private int randomNumber() {
        int count = (int) ticketService.count();
        return new Random().nextInt(count) + 1;
    }

    public String formatExamTicket(ExamTicket examTicket) {
        String fullAnswer = examTicket.getFullAnswer();
        String questionTopic = examTicket.getQuestionTopic();
        String question = examTicket.getQuestion();
        long id = examTicket.getId();
        return String.format("№%d \nТема - %s\n\nВопрос - %s\n\nОтвет\n%s",id,questionTopic,question,fullAnswer);
    }
}
