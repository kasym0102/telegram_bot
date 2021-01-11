package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import kz.home.my_bot.service.ContributionService;

@Component
public class ContributionHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private ContributionService contributionService;

    public ContributionHandler(ReplyMessagesService messagesService, ContributionService contributionService) {
        this.messagesService = messagesService;
        this.contributionService = contributionService;
    }

    @Override
    public SendMessage handle(Message message) {
        return contributionService.getMainMenuMessage(message.getChatId(), messagesService.getReplyText("reply.showMainMenu"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.CONTRIBUTION;
    }


}
