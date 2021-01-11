
package kz.home.my_bot.botapi.handlers.menu;


import kz.home.my_bot.botapi.BotState;
import kz.home.my_bot.botapi.InputMessageHandler;
import kz.home.my_bot.service.ReplyMessagesService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import kz.home.my_bot.service.ProjectsService;

@Component
public class ProjectsHandler implements InputMessageHandler {
    private ReplyMessagesService messagesService;
    private ProjectsService projectsService;

    public ProjectsHandler(ReplyMessagesService messagesService, ProjectsService projectsService) {
        this.messagesService = messagesService;
        this.projectsService = projectsService;
    }

    @Override
    public SendMessage handle(Message message) {
        return projectsService.getMainMenuMessage(message.getChatId(), messagesService.getReplyText("reply.Projects"));
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PROJECTS;
    }


}
