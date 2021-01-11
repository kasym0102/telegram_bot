package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.RegistrToEventService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class RegistrToEventHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private RegistrToEventService registrToEventService;

    public RegistrToEventHandler(ReplyMessagesService messagesService, RegistrToEventService registrToEventService) {
        this.messagesService = messagesService;
        this.registrToEventService = registrToEventService;
    }

    @Override
    public SendMessage handle(Message message) {
        return registrToEventService.getMainMenuMessage(message.getChatId(), messagesService.getReplyText("reply.showMainMenu"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.REGISTR_TO_EVENT;
    }


}
