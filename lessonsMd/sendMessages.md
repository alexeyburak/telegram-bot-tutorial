### Introduction

Welcome to the first lesson! In this lesson, we will learn how to ***send messages*** and make a simple *echo* bot.

In this tutorial I will use next characteristics:
* My home macOS laptop with IntelliJ Idea pre-installed
* Java JDK 18

### Preparation

To begin with, for the purity of the code, I recommend making a separate class where we will insert all the data received from the ***BotFather***

> If you don't know what it is, go back to lesson 0
```java
public class Bot {

    // Put your data
    public static final String TOKEN = "YOUR_TOKEN_HERE";
    public static final String USERNAME = "YOUR_USERNAME_HERE";
}
```

Next, let's connect all the necessary libraries that will be needed when creating a bot.

As for the *maven*, you should go to this repository and ***add*** maven dependency.

* [Java Library](https://github.com/rubenlagus/TelegramBots) to get last version of maven dependency

You can also copy from here:

```maven dependency
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots</artifactId>
    <version>6.0.1</version>
</dependency>
```

Also in the future we will need methods from Java Spring, so we will also connect the library

* [Spring Library](https://github.com/springfox/springfox) to get last version of spring dependency

You can also copy from here:
    
``` maven dependency
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

To add a dependency, you need to go into the **project structure**, then the **libraries**, click **plus** and **insert** the maven dependency

### Process

After we have connected all the necessary dependencies, we can proceed with the code.

First, let's create the main class.

> new HandlerToLessonOne() is our class, so there is no need to import the proposed library
```java
public class Main {

    public static void main(String[] args) {
        // Creating a bot
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Registration of our bot
            telegramBotsApi.registerBot(new HandlerToLessonOne());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
```

This completes the main function, let's start creating an update handler.

After creating the class `HandlerToLessonOne`, we need to extend `TelegramLongPollingBot` and then `Override methods` offered by it.

> You should get this code
```java
public class HandlerToLessonOne extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

    @Override
    public void onUpdateReceived(Update update) {

    }
}
```

Then we need to replace the `null` values with the values that we had in the `Bot` class
> Something like this
```java
public class HandlerToLessonOne extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Bot.TOKEN;
    }
}
```

We just need to *Override* the `onUpdateReceived` method and the *echo bot will be ready*.

> The sent message must contain chat id and the message itself!
```java
public class HandlerToLessonOne extends TelegramLongPollingBot {
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
```
After that, our bot is ready to work and will return the message you wrote!

![Show Work](images/lesson1/showWork.png)

Back to [Lesson 0](creatingBot.md) of creating a bot in BotFather  
Go to [Lesson 2](markups.md) of making buttons in the keyboard field