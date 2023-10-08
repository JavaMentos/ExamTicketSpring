package ru.home.examticketspring.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingMessageService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Log4j2
public class GetQuiz implements Consumer<Message> {
    private final TelegramService telegramService;
    private final ProcessingMessageService processingMessageService;

    @Override
    public void accept(Message message) {
        ExamTicket randomTicket = processingMessageService.getRandomTicket();
        telegramService.sendQuizPoll(randomTicket, message.getChatId().toString());
        log.info("Пользователь: id - {}, userName - {}, Выполнил команду - {}", message.getFrom().getId(), message.getFrom().getUserName(), getClass().getSimpleName());
    }
}