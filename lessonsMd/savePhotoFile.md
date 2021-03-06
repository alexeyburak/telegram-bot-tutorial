### Introduction

In this lesson, we will learn how to ***save photos and files*** to the *local* directory

If you didn't open the [Lesson 1](sendMessages.md), don't forget to make a `Bot` class with a `token` and `name` of the bot, as well as connect libraries.

This lesson will present *the simplest* possible *solution* to the problem for the simplicity of the lesson. For better code, these methods would be worth *rewriting*

### Process

The main function remains unchanged, so just copy it in [previous lessons](markups.md)

Let's look at the `HandlerToLessonFive` class

We still need to *Override* `onUpdateReceived` method

For the sake of *simplicity* of the lesson, we will not make any commands. To save the sent document, the user will *only need to send* it to the chat

> Before we started writing methods for *saving*, we will do a simple processing of the received update
```java
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
        if (update.getMessage().hasDocument()) {
            // Save file method
            saveFile(update, sendMessage);
            // If update has image
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            // Save photo method
            savePhoto(update, sendMessage);
        }
    }
}
```

If there is a *document* in the update, method `saveFile` will be called, if there is a *photo*, then method `savePhoto`

Let's start writing the methods themselves

> Let's start by *saving* the file

```java 
public class HandleToLessonFive extends TelegramLongPollingBot {
    // Previous methods
    
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
}
```

Sending a message here is *optional*, it only serves to *notify* about a successful save

First we use class `Document` to *create* the document itself, then class `GetFile` to *get* this document

> Saving a photo differs only by the *type of object* being created

```java
public class HandleToLessonFive extends TelegramLongPollingBot {
    // Previous methods

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
```

First we create a `List` of photos, then we get *the largest photo* from this list, then we get the `file id` of this photo.
Then the same saving procedure is performed as in the *previous method*

This completes the execution of the bot to save the sent photos or documents!

You can find all the code [here](https://github.com/alexeyburak/telegram-bot-tutorial/tree/main/src/com/alexeyburak/lesson5)

> *Sending* documents

![Show Work](images/lesson5/example.png)

> Document *in the local directory*

![Show Work](images/lesson5/showSave.png)


Back to [Lesson 4](sendMusic.md) of sending music from the local directory  
