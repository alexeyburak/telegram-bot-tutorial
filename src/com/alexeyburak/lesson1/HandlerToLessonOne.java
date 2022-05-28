package com.alexeyburak.lesson1;

import com.alexeyburak.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class HandlerToLessonOne extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Bot.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // If the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Initialization sendMessage
            SendMessage sendMessage = new SendMessage();
            // Set chat id to sendMessage
            sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
            // Setting the text to be sent
            sendMessage.setText(update.getMessage().getText());
            try {
                // Sending the message
                execute(sendMessage);
            } catch (TelegramApiException e) {
                System.out.println("Error in sendMessage" + e);
            }
        }
    }
}
