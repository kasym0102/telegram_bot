package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.botapi.handlers.fillingprofile.UserProfileData;
import kz.home.my_bot.cache.UserDataCache;
import kz.home.my_bot.service.ReplyMessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileWriter;
import java.io.IOException;


@Slf4j
@Component
public class CallBackHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public CallBackHandler(UserDataCache userDataCache,
                                 ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_FULL_NAME);
        }
        userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_FULL_NAME);
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CALLBACK;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.ASK_FULL_NAME)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.callback");
            userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
        }

        if (botState.equals(BotState.PROFILE_FILLED)) {
            profileData.setCountry_email_phone(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.callbackOrdered");

            try (FileWriter writer = new FileWriter("callback.txt", true)) {
                // запись всей строки
                writer.write(profileData.toString());
                // запись по символам
                writer.append('\n');

            } catch (IOException ex) {

                System.out.println(ex.getMessage());
            }

        }

        userDataCache.saveUserProfileData(userId, profileData);


        return replyToUser;
    }


}



