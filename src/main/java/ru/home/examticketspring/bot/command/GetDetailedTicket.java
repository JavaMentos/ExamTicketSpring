package ru.home.examticketspring.bot.command;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
@Log4j2
public class GetDetailedTicket implements Consumer<Message> {
    private final TelegramService telegramService;
    private final ProcessingService processingService;

    public GetDetailedTicket(@Lazy TelegramService telegramService, ProcessingService processingService) {
        this.telegramService = telegramService;
        this.processingService = processingService;
    }

    @Override
    public void accept(Message message) {
        ExamTicket randomTicket = processingService.getRandomTicket();
        String formattedText = processingService.formatExamTicket(randomTicket);
        telegramService.sendTextMessage(formattedText, String.valueOf(message.getChatId()));
        log.info("Пользователь " + message.getFrom().getId() + " Выполнил команду " + this.getClass().getName());
    }
}