package ru.home.examticketspring.botcommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
public class ExamTest implements Consumer<Message> {

    private final TelegramService telegramService;
    private final ProcessingService processingService;

    public ExamTest(@Lazy TelegramService telegramService, ProcessingService processingService) {
        this.telegramService = telegramService;
        this.processingService = processingService;
    }

    @Override
    public void accept(Message message) {
        ExamTicket randomTicket = processingService.getRandomTicket();
        telegramService.sendQuizPoll(randomTicket, String.valueOf(message.getChatId()));
    }
}