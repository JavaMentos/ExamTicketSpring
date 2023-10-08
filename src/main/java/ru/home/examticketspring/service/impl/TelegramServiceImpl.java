package ru.home.examticketspring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.examticketspring.model.SpringTicket;
import ru.home.examticketspring.service.IncomingMessageService;
import ru.home.examticketspring.service.TelegramService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class TelegramServiceImpl extends TelegramLongPollingBot implements TelegramService {
    @Value("${bot.parseMode}")
    private String parseMode;
    @Value("${bot.userName}")
    private String botName;
    @Value("${bot.token}")
    private String token;
    private IncomingMessageService incomingMessage;

    @Autowired
    @Lazy
    public void setIncomingMessage(IncomingMessageService incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()) {
            incomingMessage.handlerMessage(update);
            log.info(message.getFrom().getUserName() + " - начал взаимодействие с ботом. Текст: " + message.getText());
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

    public void sendQuizPoll(SpringTicket springTicket, String chatId) {
        String pollType = "quiz";

        List<String> answers = new ArrayList<>();
        answers.add(springTicket.getAnswer1());
        answers.add(springTicket.getAnswer2());
        answers.add(springTicket.getAnswer3());
        answers.add(springTicket.getAnswer4());

        String rightAnswer = springTicket.getAnswer1();

        Collections.shuffle(answers);

        int correctAnswer = answers.indexOf(rightAnswer);

        String formatQuestion = String.format("№ %d%n%s", springTicket.getId(), springTicket.getQuestion());

        SendPoll poll = new SendPoll();
        poll.setChatId(chatId);
        poll.setQuestion(formatQuestion);
        poll.setOptions(answers);
        poll.setCorrectOptionId(correctAnswer);
        poll.setType(pollType);
        poll.setExplanation(rightAnswer);

        try {
            execute(poll);
        } catch (TelegramApiException e) {
            log.error("Произошло исключение, вводные данные. \n chatId - {},\n formatQuestion - {},\nanswers - {} ,\ncorrectAnswer - {},\npollType - {},\nexamTicket.getRightAnswer() - {}",
                    chatId, formatQuestion, answers, correctAnswer, pollType, rightAnswer);
            e.printStackTrace();
        }
    }

    @Override
    public String createWelcomeMessage() {
        return """
                Добро пожаловать! Этот бот создан с целью помочь вам освоить фреймворк - Spring.
                Здесь вы найдете квизы и подробные ответы по следующим темам:
                
                1. Spring MVC
                2. Контейнер, IoC, бины
                3. Spring REST
                4. Spring Boot
                5. Spring Security
                6. Spring Test
                
                На данный момент, доступно только одна тема "Контейнер, IoC, бины"
                но работа идет над добавлением остальных тем.
                
                В боте реализованы основные команды:
                /getquiz получите случайный квиз.
                /getanswerticket получите случайный ответ.
                /getanswerbyid получить ответ по номеру вопроса.
                
                Если у вас возникнут вопросы, пожелания или предложения по улучшению функционала,
                не стесняйтесь обращаться ко мне - @Vasiliy_s. Я буду рад обратной связи!
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