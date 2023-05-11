package ru.home.examticketspring.impl.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.home.examticketspring.bot.command.*;
import ru.home.examticketspring.bot.state.TelegramBotState;
import ru.home.examticketspring.model.ExamTicket;
import ru.home.examticketspring.model.TelegramUser;
import ru.home.examticketspring.service.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Service
public class IncomingMessageImpl implements IncomingMessageService {
    private final TelegramService telegramService;
    private final HashMap<String, Consumer<Message>> commandMap = new HashMap<>();
    private final List<Long> listUserId;
    private final UserService userService;
    @Value("${bot.userName}")
    private String botName;
    private final TelegramBotState botState;
    private final TicketService ticketService;
    private final ProcessingService processingService;
    public static final ConcurrentHashMap<Long, TelegramUser> statisticsUser = new ConcurrentHashMap<>();

    public IncomingMessageImpl(@Lazy TelegramService telegramService, TestCommand testCommand,
                               GetChatId getChatId,
                               ExamTest examTest,
                               GetDetailedTicket getDetailedTicket,
                               UserService userService,
                               GetAnswerById getAnswerById,
                               TelegramBotState botState,
                               TicketService ticketService,
                               ProcessingService processingService) {
        commandMap.put("/testcommand", testCommand);
        commandMap.put("/getchatid", getChatId);
        commandMap.put("/examtest", examTest);
        commandMap.put("/getanswerbyid", getAnswerById);
        commandMap.put("/getdetailedticket", getDetailedTicket);

        this.telegramService = telegramService;
        this.userService = userService;
        this.botState = botState;
        this.ticketService = ticketService;
        this.processingService = processingService;

        listUserId = userService.getAllUserId();
        initializeStaticsMap();
    }

    public void handleMessage(Update update) {
        Message message = update.getMessage();
        Long userId = message.getFrom().getId();
        String text = message.getText();

        updateUserActivity(userId);

        if (!listUserId.contains(userId)) {
            createNewUser(message.getFrom());
            telegramService.sendTextMessage(telegramService.createWelcomeMessage(),String.valueOf(message.getChatId()));
        }

        if (botState.getUserIdForState() == message.getFrom().getId()) {
            switch (botState.getState()) {
                case AWAITING_ID:

                    throwElseNotNumberOrNull(text);

                    long rowsCount = ticketService.count();
                    long idRecordFromUser = Long.parseLong(text);

                    throwIfNumberIsNotInRange(rowsCount, idRecordFromUser);

                    ExamTicket examTicket = ticketService.findById(idRecordFromUser).get();
                    String formattedText = processingService.formatExamTicket(examTicket);

                    telegramService.sendTextMessage(formattedText, userId.toString());
                    botState.stateNormal();
                    break;
            }
        }

        String textHasMessage = message.getText().replace(botName, "");
        Consumer<Message> messageConsumer = commandMap.get(textHasMessage);

        if (messageConsumer != null) {
            messageConsumer.accept(message);
        }
    }

    @Override
    public synchronized void updateUserActivity(Long chatId) {
        TelegramUser user = statisticsUser.get(chatId);
        if (!user.getLastActiveDate().isEqual(LocalDate.now())) {
            user.setLastActiveDate(LocalDate.now());
        }
        user.setCounter(user.getCounter() + 1);
    }

    private String replaceNullOrEmptyWithUnknown(String field) {
        return field == null || field.isEmpty() ? "unknown" : field;
    }

    private void initializeStaticsMap() {
        List<TelegramUser> allUsers = userService.getAllUsers();
        allUsers.forEach(user -> statisticsUser.put(user.getUserId(), user));
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
        newUser.setCounter(0);

        listUserId.add(user.getId());
        userService.addUser(newUser);
    }

    private void throwElseNotNumberOrNull(String text) {
        if (text == null || !text.matches("\\d+")) {
            throw new IllegalArgumentException("Поддерживаются только цифры");
        }
    }

    private void throwIfNumberIsNotInRange(long rowsCount, long idRecordFromUser) {
        if(idRecordFromUser > rowsCount || idRecordFromUser < 1)
            throw new IllegalArgumentException("число должно быть положительным и не больше " + rowsCount);
    }
}
