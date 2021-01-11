package kz.home.my_bot.botapi.handlers.fillingprofile;

import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
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
public class FillingProfileHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public FillingProfileHandler(UserDataCache userDataCache,
                                 ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUsersCurrentBotState(message.getFrom().getId()).equals(BotState.FILLING_PROFILE)) {
            userDataCache.setUsersCurrentBotState(message.getFrom().getId(), BotState.ASK_FULL_NAME);
        }
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        SendMessage replyToUser = null;

//        if (botState.equals(BotState.ASK_FULL_NAME)) {
//            replyToUser = messagesService.getReplyMessage(chatId, "reply.askFullName");
//            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_WORK_STUDY);
//        }


        if (botState.equals(BotState.ASK_WORK_STUDY)) {
            profileData.setFull_name(usersAnswer);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.askWork");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COUNTRY_EMAIL_PHONE);
        }

        if (botState.equals(BotState.ASK_COUNTRY_EMAIL_PHONE)) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.Country");
            profileData.setWork_study(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.PROFILE_FILLED);
        }



        if (botState.equals(BotState.PROFILE_FILLED)) {
            profileData.setCountry_email_phone(usersAnswer);
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
            replyToUser = messagesService.getReplyMessage(chatId, "reply.profileFilled");

            try(FileWriter writer = new FileWriter("notes3.txt", true ))
            {
                // запись всей строки
                writer.write(profileData.toString());
                // запись по символам
                writer.append('\n');

            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }

        }

        userDataCache.saveUserProfileData(userId, profileData);



        return replyToUser;
    }


}



