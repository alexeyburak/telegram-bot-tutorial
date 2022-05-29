package com.alexeyburak.lesson4;

import com.alexeyburak.Bot;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileNotFoundException;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class HandleToLessonFour extends TelegramLongPollingBot {

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
                case "want a song" -> {
                    try {
                        sendMusic(update, "test.mp3", message);
                    } catch (FileNotFoundException | TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void sendMusic(Update update, String fileName, SendMessage sendMessage) throws FileNotFoundException, TelegramApiException {
        // Initialization sendAudio
        SendAudio sendAudio = new SendAudio();
        // In advance set chat id to sendMessage
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        // Set text to sendMessage
        sendMessage.setText("nice track!");
        // Set chat id to sendAudio
        sendAudio.setChatId(String.valueOf(update.getMessage().getChatId()));
        // Set file to sendAudio
        sendAudio.setAudio(new InputFile(ResourceUtils.getFile(fileName)));
        // Set a title of the track
        sendAudio.setTitle("name of the track");
        // Set a performer of the track
        sendAudio.setPerformer("performer of the track");
        execute(sendAudio);
    }
}
