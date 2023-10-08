package ru.home.examticketspring.bot.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TestCommand implements Consumer<Message> {

    private final TelegramService telegramService;

    @Override
    public void accept(Message message) {
        String welcomeMessage = telegramService.createWelcomeMessage();
        telegramService.sendTextMessage(welcomeMessage, message.getChatId().toString());
    }
}