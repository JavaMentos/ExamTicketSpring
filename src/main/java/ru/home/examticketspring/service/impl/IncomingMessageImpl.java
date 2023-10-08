package ru.home.examticketspring.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.home.examticketspring.bot.command.*;
import ru.home.examticketspring.bot.state.TelegramBotState;
import ru.home.examticketspring.model.SpringTicket;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.*;
import ru.home.examticketspring.utls.TelegramUserContainer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.function.Consumer;

@Service
@Log4j2
@RequiredArgsConstructor
public class IncomingMessageImpl implements IncomingMessageService {
    private TelegramService telegramService;
    private final HashMap<String, Consumer<Message>> commandMap = new HashMap<>();
    private final UserService userService;
    @Value("${bot.userName}")
    private String botName;
    private final TelegramBotState botState;
    private final TicketService ticketService;
    private final ProcessingMessageService processingMessageService;
    private final TestCommand testCommand;
    private final GetChatId getChatId;
    private final GetQuiz getQuiz;
    private final GetAnswerTicket getAnswerTicket;
    private final GetAnswerById getAnswerById;

    @Lazy
    @Autowired
    public void setTelegramService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostConstruct
    public void init() {
        TelegramUserContainer.initializeStaticsMap(userService.getAllUsers());
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/getquiz", getQuiz);
        commandMap.put("/getanswerbyid", getAnswerById);
        commandMap.put("/getanswerticket", getAnswerTicket);
    }

    public void handlerMessage(Update update) {
        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        String text = message.getText();

        TelegramUserContainer.updateUserActivity(userId);

        if (!TelegramUserContainer.isNewUsers(userId)) {
            createNewUser(message.getFrom());
            telegramService.sendTextMessage(telegramService.createWelcomeMessage(), String.valueOf(message.getChatId()));
        }

        if (botState.getUserIdForState() == message.getFrom().getId()) {
            switch (botState.getState()) {
                case AWAITING_ID:

                    try {
                        throwElseNotNumberOrNull(text);

                        long rowsCount = ticketService.count();
                        long idRecordFromUser = Long.parseLong(text);

                        throwIfNumberIsNotInRange(rowsCount, idRecordFromUser);

                        SpringTicket springTicket = ticketService.findById(idRecordFromUser).get();
                        String formattedText = processingMessageService.formatExamTicket(springTicket);

                        telegramService.sendTextMessage(formattedText, userId.toString());
                        botState.stateNormal();
                        break;
                    } catch (IllegalArgumentException e) {
                        telegramService.sendTextMessage(e.getMessage(), userId.toString());
                        botState.stateNormal();
                    }

                default:
                    break;
            }
        }

        String textHasMessage = message.getText().replace(botName, "");
        Consumer<Message> messageConsumer = commandMap.get(textHasMessage);

        if (messageConsumer != null) {
            messageConsumer.accept(message);
        }
    }

    private String replaceNullOrEmptyWithUnknown(String field) {
        return field == null || field.isEmpty() ? "unknown" : field;
    }

    private void createNewUser(User user) {
        TelegramUser newUser = new TelegramUser();

        newUser.setUserId(user.getId());

        String userName = replaceNullOrEmptyWithUnknown(user.getUserName());
        newUser.setUsername(userName);

        String firstName = replaceNullOrEmptyWithUnknown(user.getFirstName());
        newUser.setFirstName(firstName);

        String lastName = replaceNullOrEmptyWithUnknown(user.getLastName());
        newUser.setLastName(lastName);

        newUser.setLastActiveDate(LocalDate.now());
        newUser.setCounter(1);

        TelegramUserContainer.statisticsUser.put(user.getId(), newUser);
        userService.addUser(newUser);
        log.info("Добавлен новый пользователь\n" + userName + "\n" + user.getId());
    }

    private void throwElseNotNumberOrNull(String text) {
        if (text == null || !text.matches("\\d+")) {
            log.warn("Поддерживаются только цифры, ввели " + text);
            throw new IllegalArgumentException("Поддерживаются только цифры, вы ввели " + text);
        }
    }

    private void throwIfNumberIsNotInRange(long rowsCount, long idRecordFromUser) {
        if (idRecordFromUser > rowsCount || idRecordFromUser < 1) {
            log.warn("Введено не корректное число, всего строк в бд " + rowsCount + ", введено " + idRecordFromUser);
            throw new IllegalArgumentException("Число должно быть положительным и не больше " + rowsCount);
        }
    }
}