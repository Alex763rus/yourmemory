package com.example.yourmemory.model.menu.base;

import com.example.yourmemory.config.BotConfig;
import com.example.yourmemory.exception.InputLongException;
import com.example.yourmemory.model.jpa.User;
import com.example.yourmemory.model.menu.MenuActivity;
import com.example.yourmemory.service.database.UserService;
import com.example.yourmemory.service.menu.StateService;
import jakarta.persistence.MappedSuperclass;
import lombok.val;
import org.example.tgcommons.model.button.ButtonsDescription;
import org.example.tgcommons.model.wrapper.DeleteMessageWrap;
import org.example.tgcommons.model.wrapper.SendDocumentWrap;
import org.example.tgcommons.model.wrapper.SendMessageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

import static org.example.tgcommons.constant.Constant.TextConstants.EMPTY;
import static org.example.tgcommons.utils.ButtonUtils.createVerticalColumnMenu;

@MappedSuperclass
public abstract class Menu implements MenuActivity {

    @Autowired
    protected BotConfig botConfig;

    @Autowired
    protected StateService stateService;

    @Autowired
    protected UserService userService;

    private static final String DEFAULT_TEXT_ERROR = "Ошибка! Команда не найдена";

    protected List<PartialBotApiMethod> errorMessage(Update update, String message) {
        return SendMessageWrap.init()
                .setChatIdLong(update.getMessage().getChatId())
                .setText(message)
                .build().createMessageList();
    }

    protected List<PartialBotApiMethod> errorMessageDefault(Update update) {
        return SendMessageWrap.init()
                .setChatIdLong(update.getMessage().getChatId())
                .setText(DEFAULT_TEXT_ERROR)
                .build().createMessageList();
    }

    protected Integer getInputInteger(User user, Update update) {
        if (!update.hasMessage()) {
            throw new InputLongException("Отсутствует сообщение", 10);
        }
        val message = update.getMessage().getText();
        if (message == null || message.equals(EMPTY) || message.trim().length() > 10 || !checkLong(message)) {
            throw new InputLongException(message, 10);
        }
        return Integer.parseInt(message);
    }

    protected Long getInputLong(User user, Update update) {
        if (!update.hasMessage()) {
            throw new InputLongException("Отсутствует сообщение", 18);
        }
        val message = update.getMessage().getText();
        if (message == null || message.equals(EMPTY) || message.trim().length() > 18 || !checkLong(message)) {
            throw new InputLongException(message, 18);
        }
        return Long.parseLong(message);
    }

    protected Long getInputLong(User user, Update update, int maxRank, long minValue, long maxValue) {
        if (!update.hasMessage()) {
            throw new InputLongException("Отсутствует сообщение", maxRank);
        }
        val message = update.getMessage().getText();
        if (message == null || message.equals(EMPTY) || message.trim().length() > maxRank || !checkLong(message)) {
            throw new InputLongException(message, maxRank);
        }
        val result = Long.parseLong(message);
        if (result < minValue || result > maxValue) {
            throw new InputLongException(message, maxRank, minValue, maxValue);
        }
        return result;
    }

    private boolean checkLong(String value) {
        try {
            Long.parseLong(value);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    protected List<PartialBotApiMethod> createErrorDefaultMessage(User user) {
        return createMessageList(user, DEFAULT_TEXT_ERROR);
    }

    protected List<PartialBotApiMethod> createMessageList(User user, String message) {
        return List.of(this.createMessage(user, message));
    }

    protected List<PartialBotApiMethod> createMessageList(User user, String message, ButtonsDescription buttonsDescription) {
        return List.of(this.createMessage(user, message, buttonsDescription));
    }

    protected PartialBotApiMethod createMessage(User user, String message) {
        return SendMessageWrap.init()
                .setChatIdLong(user.getChatId())
                .setText(message)
                .build().createMessage();
    }

    protected PartialBotApiMethod createDeleteMessage(Update update) {
        val message = update.hasCallbackQuery() ? update.getCallbackQuery().getMessage() : update.getMessage();
        return DeleteMessageWrap.init()
                .setChatIdLong(message.getChatId())
                .setMessageId(message.getMessageId())
                .build().createMessage();
    }

    protected PartialBotApiMethod createMessage(User user, String message, ButtonsDescription buttonsDescription) {
        return createMessage(user, message, createVerticalColumnMenu(buttonsDescription));
    }

    protected PartialBotApiMethod createDocumentMessage(User user, String caption, InputFile inputFile) {
        return SendDocumentWrap.init()
                .setChatIdLong(user.getChatId())
                .setDocument(inputFile)
                .setCaption(caption)
                .build().createMessage();
    }

    protected PartialBotApiMethod createMessage(User user, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessageWrap.init()
                .setChatIdLong(user.getChatId())
                .setText(message)
                .setInlineKeyboardMarkup(inlineKeyboardMarkup)
                .build().createMessage();
    }

    protected List<PartialBotApiMethod> createMessageList(User user, String message, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return List.of(this.createMessage(user, message, inlineKeyboardMarkup));
    }


    protected List<PartialBotApiMethod> createAdminMessages(String adminMessage) {
        val admins = userService.getManagersAndAdmins();
        val answer = new ArrayList<PartialBotApiMethod>();
        admins.forEach(admin -> answer.add(createMessage(admin, adminMessage)));
        return answer;
    }
}
