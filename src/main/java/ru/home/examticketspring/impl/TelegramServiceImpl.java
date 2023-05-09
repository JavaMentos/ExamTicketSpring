package ru.home.examticketspring.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.polls.SendPoll;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.examticketspring.botcommand.ExamTest;
import ru.home.examticketspring.botcommand.GetChatId;
import ru.home.examticketspring.botcommand.GetDetailedTicket;
import ru.home.examticketspring.botcommand.TestCommand;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.TelegramService;
import ru.home.examticketspring.service.UserService;

import java.util.*;
import java.util.function.Consumer;

@Service
public class TelegramServiceImpl extends TelegramLongPollingBot implements TelegramService {
    @Value("${bot.parseMode}")
    private String parseMode;
    @Value("${bot.userName}")
    private String userName;
    @Value("${bot.token}")
    private String token;
    private final UserService userService;
    private final List<Long> listUserId;

    private final HashMap<String, Consumer<Message>> commandMap = new HashMap<>();

    public TelegramServiceImpl(TestCommand testCommand,
                               GetChatId getChatId,
                               ExamTest examTest,
                               GetDetailedTicket getDetailedTicket,
                               UserService userService) {
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/examtest", examTest);
        commandMap.put("/getdetailedticket", getDetailedTicket);
        this.userService = userService;

        listUserId = userService.getAllUserId();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage() && message.hasText()) {
            Long id = message.getFrom().getId();

            if (!listUserId.contains(id)) {
                User from = message.getFrom();
                TelegramUser newUser = new TelegramUser();
                newUser.setUserId(from.getId());

                String userName = replaceNullOrEmptyWithUnknown(from.getUserName());
                newUser.setUsername(userName);

                String firstName = replaceNullOrEmptyWithUnknown(from.getFirstName());
                newUser.setFirstName(firstName);

                String lastName = replaceNullOrEmptyWithUnknown(from.getLastName());
                newUser.setLastName(lastName);

                listUserId.add(from.getId());
                userService.addUser(newUser);

                sendTextMessage(createWelcomeMessage(),String.valueOf(message.getChatId()));
            }

            String textHasMessage = message.getText().replace(getBotUsername(), "");

            Consumer<Message> messageConsumer = commandMap.get(textHasMessage);

            if (messageConsumer != null) {
                messageConsumer.accept(message);
            }

        }
    }

    private String replaceNullOrEmptyWithUnknown(String field) {
        return field == null || field.isEmpty() ? "unknown" : field;
    }

    @Override
    public String getBotUsername() {
        return userName;
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
        poll.setQuestion(examTicket.getQuestion());
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
        return "Добро пожаловать! Этот бот создан с целью помочь вам освоить фреймворк Spring.\n\n" +
                "Здесь вы найдете квизы и подробные ответы по следующим темам:\n\n" +
                "   1. Spring MVC\n" +
                "   2. Контейнер, IoC, бины\n" +
                "   3. Spring REST\n" +
                "   4. Spring Boot\n" +
                "   5. Spring Security\n\n" +
                "На данный момент доступна только тема \"Контейнер, IoC, бины\"" +
                " но работа идет над добавлением остальных тем.\n\n" +
                "Бот предоставляет две основные команды:\n" +
                "/examtest получите случайный квиз-вопрос с 4 вариантами ответов.\n" +
                "/getdetailedticket получите случайный подробный ответ на квиз-вопрос.\n\n" +
                "Если у вас возникнут вопросы, пожелания или предложения по улучшению бота," +
                " не стесняйтесь обращаться ко мне - @Vasiliy_s. Я буду рад вашей обратной " +
                "связи и хочу сделать бота еще лучше!";

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