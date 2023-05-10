package ru.home.examticketspring.impl.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.home.examticketspring.botcommand.ExamTest;
import ru.home.examticketspring.botcommand.GetChatId;
import ru.home.examticketspring.botcommand.GetDetailedTicket;
import ru.home.examticketspring.botcommand.TestCommand;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.IncomingMessageService;
import ru.home.examticketspring.service.TelegramService;
import ru.home.examticketspring.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Service
public class IncomingMessageImpl implements IncomingMessageService {
    private final TelegramService telegramService;
    private final HashMap<String, Consumer<Message>> commandMap = new HashMap<>();
    private final List<Long> listUserId;
    private final UserService userService;
    @Value("${bot.userName}")
    private String userName;

    public IncomingMessageImpl(@Lazy TelegramService telegramService, TestCommand testCommand,
                               GetChatId getChatId,
                               ExamTest examTest,
                               GetDetailedTicket getDetailedTicket,
                               UserService userService) {
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/examtest", examTest);
        commandMap.put("/getdetailedticket", getDetailedTicket);
        this.telegramService = telegramService;
        this.userService = userService;

        listUserId = userService.getAllUserId();
    }

    public void handleMessage(Update update) {
        Message message = update.getMessage();
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

            telegramService.sendTextMessage(telegramService.createWelcomeMessage(),String.valueOf(message.getChatId()));
        }

        String textHasMessage = message.getText().replace(userName, "");

        Consumer<Message> messageConsumer = commandMap.get(textHasMessage);

        if (messageConsumer != null) {
            messageConsumer.accept(message);
        }
    }

    private String replaceNullOrEmptyWithUnknown(String field) {
        return field == null || field.isEmpty() ? "unknown" : field;
    }
}
