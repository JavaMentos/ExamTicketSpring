package ru.home.examticketspring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.home.examticketspring.model.SpringTicket;
import ru.home.examticketspring.service.ProcessingMessageService;
import ru.home.examticketspring.service.TicketService;

import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
public class ProcessingMessageServiceImpl implements ProcessingMessageService {
    private final TicketService ticketService;
    private final Random random = new Random();

    public ProcessingMessageServiceImpl(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public SpringTicket getRandomTicket() {
        int numberLine = randomNumber();
        Optional<SpringTicket> examTicket = ticketService.findById(numberLine);
        if (examTicket.isPresent()) {
            return examTicket.get();
        }
        log.error("при получении тикета из бд, произошла ошибка, номер строки " + numberLine);
        return null;
    }

    private int randomNumber() {
        int count = (int) ticketService.count();
        return random.nextInt(count) + 1;
    }

    public String formatExamTicket(SpringTicket springTicket) {
        String fullAnswer = springTicket.getFullAnswer();
        String questionTopic = springTicket.getQuestionTopic();
        String question = springTicket.getQuestion();
        long id = springTicket.getId();
        return String.format("№%d %nТема - %s%n%nВопрос - %s%n%nОтвет%n%s", id, questionTopic, question, fullAnswer);
    }
}
