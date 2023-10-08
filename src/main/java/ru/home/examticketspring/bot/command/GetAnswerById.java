package ru.home.examticketspring.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.examticketspring.bot.state.TelegramBotState;
import ru.home.examticketspring.service.TelegramService;

import java.util.function.Consumer;

/**
 * Класс GetAnswerById представляет обработчик сообщений для получения ответа по идентификатору.
 * Реализует интерфейс Consumer<Message> для обработки входящих сообщений.
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class GetAnswerById implements Consumer<Message> {
    private final TelegramBotState botState;
    private final TelegramService telegramService;

    /**
     * Метод accept принимает и обрабатывает входящее сообщение.
     *
     * @param message входящее сообщение, для обработки
     */
    @Override
    public void accept(Message message) {
        botState.setUserIdForState(message.getFrom().getId());
        String command = message.getText();
        botState.changeActivityState(command);
        telegramService.sendTextMessage("Введите номер вопроса", message.getChatId().toString());
        log.info("Пользователь: id - {}, логин - {}, Выполнил команду - {}",
                message.getFrom().getId(), message.getFrom().getUserName(), getClass().getSimpleName());
    }
}
