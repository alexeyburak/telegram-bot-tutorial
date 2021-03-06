### Introduction

In this lesson, we will learn how to ***send photos*** located in a local directory.

If you didn't open the [Lesson 1](sendMessages.md), don't forget to make a `Bot` class with a `token` and `name` of the bot, as well as connect libraries.

### Process 

The main function remains unchanged, so just copy it in [previous lessons](markups.md)

Let's look at the `HandlerToLessonThree` class.

We still need to *Override* `onUpdateReceived` method

Let's do a simple processing of the received messages

> When writing "want picture", the user will receive a photo

```java
public class HandleToLessonThree extends TelegramLongPollingBot {

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
}
```

It remains only to write the method of sending the photo itself

```java
public class HandleToLessonThree extends TelegramLongPollingBot {
    // Previous methods
    
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
```

You can find all the code [here](https://github.com/alexeyburak/telegram-bot-tutorial/tree/main/src/com/alexeyburak/lesson3)

As a result, we get a bot that, when entering a command, sends photos from the local directory

![Show Work](images/lesson3/example.png)

Back to [Lesson 2](markups.md) of getting Keyboard Markup  
Go to [Lesson 4](sendMusic.md) of sending music from local directory