package ru.home.examticketspring.botcommand;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.ProcessingService;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
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
        telegramService.sendTextMessage(formatExamTicket(randomTicket), String.valueOf(message.getChatId()));
    }

    private String formatExamTicket(ExamTicket examTicket) {
        String fullAnswer = examTicket.getFullAnswer();
        String questionTopic = examTicket.getQuestionTopic();
        String question = examTicket.getQuestion();
        return String.format("Тема - %s\n\nВопрос - %s\n\nОтвет\n%s",questionTopic,question,fullAnswer);
    }
}