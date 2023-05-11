package ru.home.examticketspring.bot.state;

import org.springframework.stereotype.Service;

@Service
public class TelegramBotState {
    private BotState state;
    private long userIdForState;

    public TelegramBotState(){
        setActivityBot(BotState.NORMAL);
    }

    public void setActivityBot(BotState state) {
        this.state = state;
    }

    public void changeActivityState(String s) {
        if (s.equalsIgnoreCase("/getanswerbyid")) {
            setActivityBot(BotState.AWAITING_ID);
        }
    }

    public void stateNormal(){
        setActivityBot(BotState.NORMAL);
    }

    public BotState getState() {
        return state;
    }

    public long getUserIdForState() {
        return userIdForState;
    }

    public void setUserIdForState(long userIdForState) {
        this.userIdForState = userIdForState;
    }

    public enum BotState{
        NORMAL,
        AWAITING_ID
    }
}