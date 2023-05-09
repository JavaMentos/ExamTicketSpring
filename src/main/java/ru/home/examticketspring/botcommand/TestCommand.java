package ru.home.examticketspring.botcommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.impl.TelegramServiceImpl;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.TicketService;

import java.util.Optional;
import java.util.function.Consumer;

@Service
public class TestCommand implements Consumer<Message> {

    private final TelegramServiceImpl telegramService;
    private final TicketService ticketService;

    public TestCommand(@Lazy TelegramServiceImpl telegramService, TicketService ticketService) {
        this.telegramService = telegramService;
        this.ticketService = ticketService;
    }

    @Override
    public void accept(Message message) {
        String text = String.format("test command correct");

        Optional<ExamTicket> byId = ticketService.findById(3);

        telegramService.sendQuizPoll(byId.get(), message.getChatId().toString());
//        for (int i = 1; i <= 23; i++) {
//
//
//            Optional<ExamTicket> byId = ticketService.findById(i);
//            telegramService.sendTextMessage(byId.get().getFullAnswer(), String.valueOf(message.getChatId()));
//            System.out.println(i);
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
        }
}