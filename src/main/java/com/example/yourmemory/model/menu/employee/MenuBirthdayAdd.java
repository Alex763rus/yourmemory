package com.example.yourmemory.model.menu.employee;

import com.example.yourmemory.model.jpa.*;
import com.example.yourmemory.model.menu.base.Menu;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.yourmemory.constant.Constant.Command.COMMAND_BIRTHDAY_ADD;
import static com.example.yourmemory.constant.ReminderStatus.OPEN;
import static com.example.yourmemory.enums.State.*;
import static org.example.tgcommons.utils.DateConverterUtils.convertDateFormat;

@Component(COMMAND_BIRTHDAY_ADD)
@Slf4j
public class MenuBirthdayAdd extends Menu {

    @Override
    public String getMenuComand() {
        return COMMAND_BIRTHDAY_ADD;
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
                case BIRTHDAY_WAIT_DATE -> waitDateLogic(user, update);
                case BIRTHDAY_WAIT_DESCRIPTION -> waitDescriptionLogic(user, update);
                default -> errorMessageDefault(update);
            };
        } catch (Exception ex) {
            log.error(ex.toString());
            return errorMessage(update, ex.toString());
        }
    }

    private List<PartialBotApiMethod> freelogic(User user, Update update) {


        stateService.setState(user, BIRTHDAY_WAIT_FIO);
        return createMessageList(user, "Введите ФИО именинника:");
    }


    private List<PartialBotApiMethod> waitDescriptionLogic(User user, Update update) {
        if (!update.hasMessage()) {
            return createMessageList(user, "Сообщение должно содержать текстовое описание. Повторите, пожалуйста, ввод");
        }
        val birthdayCard = birthdayCardMapTmp.get(user);
        birthdayCard.setDescription(update.getMessage().getText());
        birthdayCard.setUser(user);
        birthdayCardRepository.save(birthdayCard);
        reminderPlanRepository.saveAll(generateReminderPlans(birthdayCard));
        stateService.setState(user, FREE);
        return createMessageList(user, birthdayCard.toString("Новое событие успешно сохранено:"));
    }

    private List<ReminderPlan> generateReminderPlans(BirthdayCard birthdayCard){
        return List.of(
                generateReminderPlan(birthdayCard, 0, 10),
                generateReminderPlan(birthdayCard, 1, 10),
                generateReminderPlan(birthdayCard, 7, 10)
        );
    }

    private ReminderPlan generateReminderPlan(BirthdayCard birthdayCard, int prevDayCount, int hour){
        val reminderPlan = new ReminderPlan();
        reminderPlan.setBirthdayCard(birthdayCard);
        reminderPlan.setReminderStatus(OPEN);
        val dateBirthday = birthdayCard.getBirthday();
        val currentDate = LocalDateTime.now();
        val date = LocalDate.of(currentDate.getYear(), dateBirthday.getMonth(), dateBirthday.getDayOfMonth());
        val reminderDate = date.minusDays(prevDayCount);
        reminderPlan.setTurnsYearsOld((long) (currentDate.getYear() - dateBirthday.getYear()));
        val time = LocalTime.of(hour, 00);
        reminderPlan.setReminderDateTime(LocalDateTime.of(reminderDate, time));
        return reminderPlan;
    }


    private List<PartialBotApiMethod> waitDateLogic(User user, Update update) {
        if (!update.hasMessage()) {
            return createMessageList(user, "Сообщение должно содержать ФИО именинника. Повторите, пожалуйста, ввод");
        }
        val birthdayCard = birthdayCardMapTmp.get(user);
        try {
            val date = convertDateFormat(update.getMessage().getText(), "yyyyMMdd");
            birthdayCard.setBirthday(LocalDate.ofInstant(
                    date.toInstant(), ZoneId.systemDefault()));
        } catch (ParseException e) {
            return createMessageList(user, "Введенные данные невозможно преобразовать к дате." +
                    " Повторите, пожалуйста, ввод в формате yyyymmdd");
        }
        stateService.setState(user, BIRTHDAY_WAIT_DESCRIPTION);
        return createMessageList(user, "Введите текстовое описание");
    }

    private List<PartialBotApiMethod> waitFioLogic(User user, Update update) {
        if (!update.hasMessage()) {
            return createMessageList(user, "Сообщение должно содержать ФИО именинника. Повторите, пожалуйста, ввод");
        }
        val birthdayCard = new BirthdayCard();
        birthdayCard.setFio(update.getMessage().getText());
        birthdayCardMapTmp.put(user, birthdayCard);
        stateService.setState(user, BIRTHDAY_WAIT_DATE);
        return createMessageList(user, "Введите дату дня рождения в формате yyyymmdd:");
    }

    @Override
    public String getDescription() {
        return getMenuComand();
    }

}
