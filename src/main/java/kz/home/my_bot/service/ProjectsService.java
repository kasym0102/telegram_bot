
package kz.home.my_bot.service;

        import kz.home.my_bot.botapi.BotState;
        import kz.home.my_bot.botapi.TelegramFacade;
        import org.springframework.stereotype.Service;
        import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
        import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

        import java.util.ArrayList;
        import java.util.List;

@Service
public class ProjectsService {

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();
        final SendMessage mainMenuMessage =
                createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);

        return mainMenuMessage;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        KeyboardRow row4 = new KeyboardRow();
        row1.add(new KeyboardButton("Наука и Бизнес"));
        row2.add(new KeyboardButton("Энциклопедия «Новый мир"));
        row3.add(new KeyboardButton("Сборник стихов А. Ахматовой"));
        row4.add(new KeyboardButton("<< Назад"));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        keyboard.add(row4);

        TelegramFacade.prev = BotState.SHOW_ABOUT_ASSOC.toString();

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}
