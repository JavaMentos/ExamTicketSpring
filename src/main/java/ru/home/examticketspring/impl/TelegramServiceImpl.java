package ru.home.examticketspring.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.service.IncomingMessageService;
import ru.home.examticketspring.service.TelegramService;

import java.util.*;

@Controller
@Log4j2
public class TelegramServiceImpl extends TelegramLongPollingBot implements TelegramService {
    @Value("${bot.parseMode}")
    private String parseMode;
    @Value("${bot.userName}")
    private String botName;
    @Value("${bot.token}")
    private String token;
    private final IncomingMessageService incomingMessage;

    public TelegramServiceImpl(IncomingMessageService incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        log.info(message.getFrom().getUserName() + " - начал взаимодействие с ботом. Текст: " + message.getText());

        if (update.hasMessage() && message.hasText()) {
            incomingMessage.handleMessage(update);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendQuizPoll(ExamTicket examTicket, String chatId) {
        String pollType = "quiz";

        List<String> answers = new ArrayList<>();

        answers.add(examTicket.getAnswer1());
        answers.add(examTicket.getAnswer2());
        answers.add(examTicket.getAnswer3());
        answers.add(examTicket.getAnswer4());

        Collections.shuffle(answers);

        int correctAnswer = answers.indexOf(examTicket.getRightAnswer());

        SendPoll poll = new SendPoll();
        poll.setChatId(chatId);
        poll.setQuestion("№" + examTicket.getId() + "\n" + examTicket.getQuestion());
        poll.setOptions(answers);

        poll.setCorrectOptionId(correctAnswer);
        poll.setType(pollType);
        poll.setExplanation(examTicket.getRightAnswer());

        try {
            execute(poll);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String createWelcomeMessage() {
        return """
                Добро пожаловать! Этот бот создан с целью помочь вам освоить фреймворк Spring.
                Здесь вы найдете квизы и подробные ответы по следующим темам:
                
                1. Spring MVC
                2. Контейнер, IoC, бины
                3. Spring REST
                4. Spring Boot
                5. Spring Security
                
                На данный момент, доступно только одна тема "Контейнер, IoC, бины"
                но работа идет над добавлением остальных тем.
                
                В боте реализованы основные команды:
                /examtest получите случайный квиз-вопрос с 4 вариантами ответов.
                /getdetailedticket получите случайный подробный ответ на квиз-вопрос.
                /getanswerbyid получить подробный ответ на квиз вопрос по номеру.
                
                Если у вас возникнут вопросы, пожелания или предложения по улучшению бота,
                не стесняйтесь обращаться ко мне - @Vasiliy_s. Я буду рад вашей обратной связи!
                """;
    }

    public void sendTextMessage(String text, String chatId) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode(parseMode)
                    .text(text)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}