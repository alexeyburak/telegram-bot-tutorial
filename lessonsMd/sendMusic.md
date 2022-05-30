### Introduction

In this lesson, we will learn how to ***send tracks*** located in a local directory.

If you didn't open the [Lesson 1](sendMessages.md), don't forget to make a `Bot` class with a `token` and `name` of the bot, as well as connect libraries.

### Process

The main function remains unchanged, so just copy it in [previous lessons](markups.md)

Let's look at the `HandlerToLessonFour` class

We still need to *Override* `onUpdateReceived` method

Let's take `onUpdateReceived` method from the last lesson and just change the name of the command

> We should get something like this:
```java
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
}
```

It remains only to write the method of sending the music itself from the local directory

> Don't forget to set chat id when we're sending something

```java 
public class HandleToLessonFour extends TelegramLongPollingBot {

    // Previous methods

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
```

You can find all the code [here](https://github.com/alexeyburak/telegram-bot-tutorial/tree/main/src/com/alexeyburak/lesson4)

As a result, we get a bot that, when entering a command, sends tracks from the local directory

![Show Work](images/lesson4/showWork.png)

Back to [Lesson 3](sendPhoto.md) of sending photos from the local directory  
Go to [Lesson 5](savePhotoFile.md) of saving files to the local directory
