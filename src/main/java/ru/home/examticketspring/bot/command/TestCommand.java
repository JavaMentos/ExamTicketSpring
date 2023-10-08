package ru.home.examticketspring.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TestCommand implements Consumer<Message> {

    private final TelegramService telegramService;
//    private final TicketService ticketService;


    @Override
    public void accept(Message message) {
        String welcomeMessage = telegramService.createWelcomeMessage();
        telegramService.sendTextMessage(welcomeMessage,message.getChatId().toString());

//        Optional<ExamTicket> byId = ticketService.findById(3);
//
//        telegramService.sendQuizPoll(byId.get(), message.getChatId().toString());
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