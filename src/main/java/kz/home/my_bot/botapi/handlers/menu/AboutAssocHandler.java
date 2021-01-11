
package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.AboutAssocService;

@Component
public class AboutAssocHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private AboutAssocService aboutAssocService;

    public AboutAssocHandler(ReplyMessagesService messagesService, AboutAssocService mainMenuService) {
        this.messagesService = messagesService;
        this.aboutAssocService = mainMenuService;
    }

    @Override
    public SendMessage handle(Message message) {
        return aboutAssocService.getMainMenuMessage(message.getChatId(), messagesService.getReplyText("reply.aboutAssociation"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_ABOUT_ASSOC;
    }


}
