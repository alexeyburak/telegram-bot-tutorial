package com.alexeyburak.lesson3;

import com.alexeyburak.Bot;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class HandleToLessonThree extends TelegramLongPollingBot {

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
            // Initialization received message
            String message_text = update.getMessage().getText();
            // Initialization sendMessage
            SendMessage message = new SendMessage();
            // In advance set chat id to sendMessage
            message.setChatId(update.getMessage().getChatId().toString());
            switch (message_text) {
                case "/start" -> message.setText("All is working");
                case "want picture" -> {
                    try {
                        sendPhoto(update, "test.png");
                    } catch (TelegramApiException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    message.setText("Photo is sent");
                }
            }
        }
    }

    public void sendPhoto(Update update, String imagePath) throws TelegramApiException, FileNotFoundException {
        // Initialization sendPhoto
        SendPhoto sendPhoto = new SendPhoto();
        File file;
        // Initialization file
        file = ResourceUtils.getFile(String.valueOf(new File(imagePath)));
        // In advance set chat id to sendPhoto
        sendPhoto.setChatId(update.getMessage().getChatId().toString());
        // Set file to sendPhoto
        sendPhoto.setPhoto(new InputFile(file));
        // Send photo
        execute(sendPhoto);
    }
}
