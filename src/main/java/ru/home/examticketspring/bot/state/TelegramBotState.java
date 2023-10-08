package ru.home.examticketspring.bot.state;

import org.springframework.stereotype.Service;

/**
 * Класс TelegramBotState представляет состояние бота для обработки сообщений.
 * Определяет текущее состояние бота и идентификатор пользователя для состояния.
 */
@Service
public class TelegramBotState {
    private BotState state;
    private long userIdForState;

    /**
     * Конструктор TelegramBotState устанавливает начальное состояние бота как NORMAL.
     */
    public TelegramBotState(){
        setActivityBot(BotState.NORMAL);
    }

    /**
     * Метод setActivityBot устанавливает активное состояние бота.
     *
     * @param state состояние бота
     */
    public void setActivityBot(BotState state) {
        this.state = state;
    }

    /**
     * Метод changeActivityState изменяет активное состояние бота на основе переданной команды.
     *
     * @param s команда для изменения состояния бота
     */
    public void changeActivityState(String s) {
        if (s.equalsIgnoreCase("/getanswerbyid")) {
            setActivityBot(BotState.AWAITING_ID);
        }
    }

    /**
     * Метод stateNormal устанавливает активное состояние бота как NORMAL.
     */
    public void stateNormal(){
        setActivityBot(BotState.NORMAL);
    }

    /**
     * Метод getState возвращает текущее состояние бота.
     *
     * @return текущее состояние бота
     */
    public BotState getState() {
        return state;
    }

    /**
     * Метод getUserIdForState возвращает идентификатор пользователя для текущего состояния бота.
     *
     * @return идентификатор пользователя для текущего состояния бота
     */
    public long getUserIdForState() {
        return userIdForState;
    }

    /**
     * Метод setUserIdForState устанавливает идентификатор пользователя для текущего состояния бота.
     *
     * @param userIdForState идентификатор пользователя для текущего состояния бота
     */
    public void setUserIdForState(long userIdForState) {
        this.userIdForState = userIdForState;
    }

    /**
     * Перечисление BotState определяет возможные состояния бота.
     */
    public enum BotState{
        NORMAL,
        AWAITING_ID
    }
}