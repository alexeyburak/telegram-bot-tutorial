package com.alexeyburak.lesson5;

import com.alexeyburak.Bot;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Bot tutorial
 * Created by Alexey Burak
 */

public class HandleToLessonFive extends TelegramLongPollingBot {
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
        // Initialize sendMessage
        SendMessage sendMessage = new SendMessage();
        // If update has document
        if (update.getMessage().hasDocument()){
            // Save file method
            saveFile(update, sendMessage);
            // If update has image
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Save photo method
            savePhoto(update, sendMessage);
        }
    }

    public void saveFile (Update update, SendMessage sendMessage) {
        // Set chat id to send message
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        // Set text to send message
        sendMessage.setText("file saved!");
        // Get document from update
        String getID = String.valueOf(update.getMessage().getFrom().getId());
        // Initialize document
        Document document = new Document();
        // Set mime type
        document.setMimeType(update.getMessage().getDocument().getMimeType());
        // Set file name
        document.setFileName(update.getMessage().getDocument().getFileName());
        // Set file size
        document.setFileSize(update.getMessage().getDocument().getFileSize());
        // Set file id
        document.setFileId(update.getMessage().getDocument().getFileId());
        // Initialize get file
        GetFile getFile = new GetFile();
        // Set file id
        getFile.setFileId(document.getFileId());
        try {
            // The import takes place so that you correctly import the necessary library from yourself
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            // Download file
            downloadFile(file, new File("YOUR_PATH"+getID+"_"+update.getMessage().getDocument().getFileName()));
            // Send message
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void savePhoto(Update update, SendMessage sendMessage) {
        // Set chat id to send message
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        // Set text to send message
        sendMessage.setText("photo saved!");
        // Make list of photos
        List<PhotoSize> photos = update.getMessage().getPhoto();
        // get photo id
        String f_id = Objects.requireNonNull(photos.stream().max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null)).getFileId();
        // Initialize get file
        GetFile getFile = new GetFile();
        // Set file id
        getFile.setFileId(f_id);
        try {
            // The import takes place so that you correctly import the necessary library from yourself
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            // Download file
            downloadFile(file, new File("YOUR_PATH"+f_id));
            // Send message
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
