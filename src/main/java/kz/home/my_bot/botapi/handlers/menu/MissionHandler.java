
package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.botapi.TelegramFacade;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MissionHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;

    public MissionHandler(ReplyMessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        TelegramFacade.prev = BotState.SHOW_MAIN_MENU.toString();

        return  new SendMessage(message.getChatId(), messagesService.getReplyText("reply.mission"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MISSION;
    }

}