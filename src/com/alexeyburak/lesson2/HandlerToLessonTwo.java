package com.alexeyburak.lesson2;

import com.alexeyburak.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class HandlerToLessonTwo  extends TelegramLongPollingBot {

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            // Initialization Keyboard Markup
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set button, Row 1 Button 1
            row.add("want cute message");
            // Set button, Row 1 Button 2
            row.add("want picture");
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
            // Set button, Row 2 Button 1
            row.add("want a song");
            // Add the second row to the keyboard
            keyboard.add(row);
            // Then you can add the number of buttons you need by analogy

            // Set keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            keyboardMarkup.setResizeKeyboard(true);

            // Initialization received message
            String message_text = update.getMessage().getText();
            // Initialization sendMessage
            SendMessage message = new SendMessage();
            // In advance set chat id to sendMessage
            message.setChatId(update.getMessage().getChatId().toString());

            switch(message_text) {
                case "/start" -> {
                    message.setReplyMarkup(keyboardMarkup);
                    message.setChatId(update.getMessage().getChatId().toString());
                }
                case "want picture" -> message.setText("It`s working!");
                // The following commands are added by analogy
                default -> {
                    message.setReplyToMessageId(update.getMessage().getMessageId());
                    message.setText("no such command");
                }
            }
            try {
                execute(message); // Sending message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}

