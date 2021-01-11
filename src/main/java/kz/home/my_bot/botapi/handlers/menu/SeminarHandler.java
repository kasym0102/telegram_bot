package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.TelegramFacade;
import kz.home.my_bot.service.MainMenuService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import kz.home.my_bot.botapi.InputMessageHandler;

import java.util.ArrayList;
import java.util.List;

@Component
public class SeminarHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;

    public SeminarHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;

    }
    @Override
    public SendMessage handle(Message message) {
        TelegramFacade.prev = BotState.PROJECTS.toString();


        return processUsersInput(message);

//        return new SendMessage(message.getChatId(),"Перейди на наш канал", new InlineKeyboardButton("Канал Ассоциаций"));
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.seminar");
        replyToUser.setReplyMarkup(getInlineMessageButtons());

        return replyToUser;
    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Начать регистрацию");
        //Every button must have callBackData, or else not work !
        buttonYes.setCallbackData("buttonYes");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonYes);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SEMINAR;
    }


}
