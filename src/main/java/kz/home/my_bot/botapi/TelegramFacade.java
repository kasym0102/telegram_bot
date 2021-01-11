package kz.home.my_bot.botapi;

import kz.home.my_bot.service.MainMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import kz.home.my_bot.cache.UserDataCache;

@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private MainMenuService mainMenuService;
    public static String prev = "SHOW_MAIN_MENU";
    public static String prev2 = "SHOW_MAIN_MENU";


    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, MainMenuService mainMenuService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }


        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

       prev2 = userDataCache.getUsersCurrentBotState(userId).toString();

        switch (inputMsg) {
            case "/start":
                botState = BotState.SHOW_MAIN_MENU;
                break;
            case "Новости Ассоциации":
                botState = BotState.NEWS;
                break;
            case "Наука и Бизнес":
                botState = BotState.SCIENCE;
                break;
            case "Энциклопедия «Новый мир":
                botState = BotState.NEW_WORLD;
                break;
            case "Сборник стихов А. Ахматовой":
                botState = BotState.POEMS;
                break;
            case "Регистрация на ивент":
                botState = BotState.REGISTR_TO_EVENT;
                break;
            case "Об Ассоциации":
                botState = BotState.SHOW_ABOUT_ASSOC;
                break;
            case "Вступить в Ассоциацию":
                botState = BotState.JOIN_THE_ASSOC;
                break;
            case "Семинар ХХХ":
                botState = BotState.SEMINAR;
                break;
            case "Заказать обратный звонок":
                botState = BotState.CALLBACK;
                break;
            case "Проекты":
                botState = BotState.PROJECTS;
                break;
            case "Внести взнос":
                botState = BotState.CONTRIBUTION;
                break;
            case "Благотворительный взнос":
                botState = BotState.Charitable_DONATE;
                break;
            case "Взнос на научные цели":
                botState = BotState.SCIENCE_DONATE;
                break;
            case "<< Назад":
                botState = BotState.valueOf(prev);
                break;
            case "Контакты":
                botState = BotState.CONTACTS;
                break;
            case "Алматы":
                botState = BotState.ALMATY;
                break;
            case "Астана":
                botState = BotState.ASTANA;
                break;
             case "Направления работы":
                botState = BotState.AREAS_OF_WORK;
                break;
            case "Название ивента 2":
            case "Название ивента 3":
            case "Название ивента 4":
                botState = BotState.NO_EVENT;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        if (userDataCache.getUsersCurrentBotState(userId).equals(BotState.NEWS)){
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }
        return replyMessage;
    }


    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "Воспользуйтесь главным меню");


        //From Destiny choose buttons
        if (buttonQuery.getData().equals("buttonYes")) {
            callBackAnswer = new SendMessage(chatId, "Напишите, пожалуйста, своё полное ФИО.");
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_WORK_STUDY);
        } else {
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MAIN_MENU);
        }

        return callBackAnswer;

    }


    private AnswerCallbackQuery sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        return answerCallbackQuery;
    }


}
