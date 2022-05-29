package com.alexeyburak.lesson5;

import com.alexeyburak.lesson4.HandleToLessonFour;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class Main {

    public static void main(String[] args) {
        // Creating a bot
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Registration of our bot
            // new Handler() is our class, so there is no need to import the proposed library
            telegramBotsApi.registerBot(new HandleToLessonFive());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
