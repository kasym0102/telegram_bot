package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.botapi.TelegramFacade;
import kz.home.my_bot.service.MainMenuService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScienceDonateHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private MainMenuService mainMenuService;

    public ScienceDonateHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {

        TelegramFacade.prev = BotState.CONTRIBUTION.toString();

        return processUsersInput(message);

    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getChatId();

        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.ScienceDonate");
        replyToUser.setReplyMarkup(getInlineMessageButtons());

        return replyToUser;
    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonYes = new InlineKeyboardButton().setText("Осуществить взнос").setUrl("https://kaspi.kz/pay/mobile/check#clear");

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
        return BotState.SCIENCE_DONATE;
    }


}