package ru.home.examticketspring.botcommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.impl.TelegramServiceImpl;

import java.util.function.Consumer;

@Component
public class TestCommand implements Consumer<Message> {

    @Autowired
    @Lazy
    private TelegramServiceImpl telegramService;

    @Override
    public void accept(Message message) {
        String text = String.format("test command correct");
        telegramService.sendTextMessage(text, String.valueOf(message.getChatId()));
    }
}