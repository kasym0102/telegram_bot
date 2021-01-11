package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.JoinAssocService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class JoinAssocHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private JoinAssocService joinAssocHandler;

    public JoinAssocHandler(ReplyMessagesService messagesService, JoinAssocService mainMenuService) {
        this.messagesService = messagesService;
        this.joinAssocHandler = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return joinAssocHandler.getMainMenuMessage(message.getChatId());
    }

    @Override
    public BotState getHandlerName() {
        return BotState.JOIN_THE_ASSOC;
    }


}
