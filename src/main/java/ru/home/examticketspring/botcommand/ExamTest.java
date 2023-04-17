package ru.home.examticketspring.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Component
public class ExamTest implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramService telegramService;
    @Autowired
    private ProcessingService ProcessingService;

    @Override
    public void accept(Message message) {
        ExamTicket randomTicket = ProcessingService.getRandomTicket();
        telegramService.sendQuizPoll(randomTicket, String.valueOf(message.getChatId()));
    }
}