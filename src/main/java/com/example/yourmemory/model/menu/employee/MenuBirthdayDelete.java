package com.example.yourmemory.model.menu.employee;

import com.example.yourmemory.model.jpa.BirthdayCard;
import com.example.yourmemory.model.jpa.BirthdayCardRepository;
import com.example.yourmemory.model.jpa.ReminderPlanRepository;
import com.example.yourmemory.model.jpa.User;
import com.example.yourmemory.model.menu.base.Menu;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.tgcommons.model.button.Button;
import org.example.tgcommons.model.button.ButtonsDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.yourmemory.constant.Constant.Command.COMMAND_BIRTHDAY_DELETE;
import static com.example.yourmemory.constant.Constant.MenuChooseTime.BACK;
import static com.example.yourmemory.enums.State.*;

@Component(COMMAND_BIRTHDAY_DELETE)
@Slf4j
public class MenuBirthdayDelete extends Menu {

    @Override
    public String getMenuComand() {
        return COMMAND_BIRTHDAY_DELETE;
    }

    private Map<User, BirthdayCard> birthdayCardMapTmp = new HashMap<>();

    @Autowired
    private ReminderPlanRepository reminderPlanRepository;

    @Autowired
    private BirthdayCardRepository birthdayCardRepository;

    @Override
    public List<PartialBotApiMethod> menuRun(User user, Update update) {
        try {
            return switch (stateService.getState(user)) {
                case FREE -> freelogic(user, update);
                case BIRTHDAY_WAIT_FIO -> waitFioLogic(user, update);
                default -> errorMessageDefault(update);
            };
        } catch (Exception ex) {
            log.error(ex.toString());
            return errorMessage(update, ex.toString());
        }
    }

    private List<PartialBotApiMethod> freelogic(User user, Update update) {
        birthdayCardRepository.findAllByUserOrderByBirthdayAsc(user);
        val birthdayCards = birthdayCardRepository.findAllByUserOrderByBirthdayAsc(user);
        val buttons = new ArrayList<Button>(
                birthdayCards.stream()
                        .map(birthdayCard -> Button.init()
                                .setKey(String.valueOf(birthdayCard.getBirthdayCardId()))
                                .setValue(birthdayCard.getFio())
                                .build())
                        .collect(Collectors.toCollection(ArrayList::new)));

        buttons.add(Button.init().setKey(BACK).setValue("Назад").build());
        val buttonDescription = ButtonsDescription.init()
                .setCountColumn(1)
                .setButtons(buttons).build();
        stateService.setState(user, BIRTHDAY_WAIT_FIO);
        return createMessageList(user, "Выберите удаляемый день рождения:", buttonDescription);
    }

    private List<PartialBotApiMethod> waitFioLogic(User user, Update update) {
        if (!update.hasCallbackQuery()) {
            return List.of(createDeleteMessage(update));
        }
        val data = update.getCallbackQuery().getData();
        if (data.equals(BACK)) {
            stateService.setState(user, FREE);
            return List.of(createDeleteMessage(update));
        }
        stateService.setState(user, FREE);
        throw new RuntimeException("Ошибка! Необходима раелизация!");
//        TODO
//        val birthdayCard = birthdayCardRepository.findById(data);
//        reminderPlanRepository.deleteByBirthdayCardIn(bi);
//        birthdayCardRepository.deleteById(Long.parseLong(data));
//
//        val birthdayCard = new BirthdayCard();
//        birthdayCard.setFio(update.getMessage().getText());
//        birthdayCardMapTmp.put(user, birthdayCard);
//        stateService.setState(user, BIRTHDAY_WAIT_DATE);
//        return createMessageList(user, "Введите дату дня рождения в формате yyyymmdd:");
    }

    @Override
    public String getDescription() {
        return getMenuComand();
    }

}
