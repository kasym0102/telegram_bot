package kz.home.my_bot.botapi.handlers.menu;

import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.ContactsService;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class ContactsHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private ContactsService contactsService;

    public ContactsHandler(ReplyMessagesService messagesService, ContactsService contactsService ) {
        this.messagesService = messagesService;
        this.contactsService = contactsService;
    }

    @Override
    public SendMessage handle(Message message) {
        return contactsService.getMainMenuMessage(message.getChatId(), messagesService.getReplyText("reply.contacts"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CONTACTS;
    }


}
