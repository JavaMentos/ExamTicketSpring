package ru.home.examticketspring.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.SpringTicket;
import ru.home.examticketspring.service.ProcessingMessageService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
@Log4j2
@RequiredArgsConstructor
public class GetAnswerTicket implements Consumer<Message> {
    private final TelegramService telegramService;
    private final ProcessingMessageService processingMessageService;

    @Override
    public void accept(Message message) {
        SpringTicket randomTicket = processingMessageService.getRandomTicket();
        String formattedText = processingMessageService.formatExamTicket(randomTicket);
        telegramService.sendTextMessage(formattedText, message.getChatId().toString());
        log.info("Пользователь: id - {}, логин - {}, Выполнил команду - {}", message.getFrom().getId(), message.getFrom().getUserName(), getClass().getSimpleName());
    }
}