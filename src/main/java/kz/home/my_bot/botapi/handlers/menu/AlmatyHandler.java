
package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.TelegramFacade;
import kz.home.my_bot.service.RegistrToEventService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
@Component
public class AlmatyHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;

    public AlmatyHandler(ReplyMessagesService messagesService, RegistrToEventService registrToEventService) {
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        TelegramFacade.prev = BotState.CONTACTS.toString();
        return  new SendMessage(message.getChatId(), messagesService.getReplyText("reply.almaty"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ALMATY;
    }

}