package ru.home.examticketspring.bot.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.bot.state.TelegramBotState;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
public class GetAnswerById implements Consumer<Message> {
    private final TelegramBotState botState;
    private final TelegramService telegramService;
    @Value("${bot.userName}")
    private String botName;

    public GetAnswerById(@Lazy TelegramService telegramService, TelegramBotState botState) {
        this.telegramService = telegramService;
        this.botState = botState;
    }

    @Override
    public void accept(Message message) {
        botState.setUserIdForState(message.getFrom().getId());
        String command = message.getText().replace(botName, "").trim();
        botState.changeActivityState(command);
        telegramService.sendTextMessage("Введите номер вопроса", message.getChatId().toString());
    }
}
