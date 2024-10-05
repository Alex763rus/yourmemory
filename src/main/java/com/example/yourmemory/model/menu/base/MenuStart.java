package com.example.yourmemory.model.menu.base;

import com.example.yourmemory.model.jpa.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.example.yourmemory.constant.Constant.Command.*;
import static org.example.tgcommons.constant.Constant.Command.COMMAND_START;
import static org.example.tgcommons.constant.Constant.TextConstants.NEW_LINE;
import static org.example.tgcommons.utils.StringUtils.prepareShield;

@Component(COMMAND_START)
@Slf4j
public class MenuStart extends Menu {

    @Override
    public String getMenuComand() {
        return COMMAND_START;
    }

    @Override
    public PartialBotApiMethod replaceButton(Update update, User user) {
        return createDeleteMessage(update);
    }

    @Override
    public List<PartialBotApiMethod> menuRun(User user, Update update) {
        try {
            return switch (user.getUserRole()) {
                case BLOCKED -> createMessageList(user, "Доступ запрещен");
                case EMPLOYEE -> getEmployeeMenu(user);
                case MANAGER -> getManagerMenu(user);
                case ADMIN -> getAdminMenu(user);
            };
        } catch (Exception ex) {
            log.error(ex.toString());
            return createMessageList(user, ex.toString());
        }
    }

    public static StringBuilder getEmployeeMenuText() {
        val messageText = new StringBuilder();
        messageText
                .append("*Дни рождения:* ").append(NEW_LINE)
                .append("- ").append("показать список: ").append(prepareShield(COMMAND_BIRTHDAY_SHOW)).append(NEW_LINE)
                .append("- ").append("добавить: ").append(prepareShield(COMMAND_BIRTHDAY_ADD)).append(NEW_LINE)
                .append("- ").append("удалить: ").append(prepareShield(COMMAND_BIRTHDAY_DELETE)).append(NEW_LINE)
        ;
        return messageText;
    }

    private List<PartialBotApiMethod> getEmployeeMenu(User user) {
        return createMessageList(user, getEmployeeMenuText().toString());
    }

    public static StringBuilder getManagerMenuText() {
        val managerMenuText = getEmployeeMenuText();
        managerMenuText.append(NEW_LINE)
                .append(NEW_LINE)
                .append("*Меню менеджера:*").append(NEW_LINE)
        ;
        return managerMenuText;
    }

    private List<PartialBotApiMethod> getManagerMenu(User user) {
        return createMessageList(user, getManagerMenuText().toString());
    }

    private List<PartialBotApiMethod> getAdminMenu(User user) {
        val adminMenuText = getManagerMenuText();
        adminMenuText.append(NEW_LINE)
                .append("*Меню администратора:*").append(NEW_LINE)
                .append("- ").append("Выгрузить все записи").append(" ").append(prepareShield("COMMAND_EXPORT_ALL_RECEPTION")).append(NEW_LINE)
        ;
        return createMessageList(user, adminMenuText.toString());
    }

    @Override
    public String getDescription() {
        return " Начало работы";
    }
}
