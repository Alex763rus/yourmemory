package com.example.yourmemory.service.sheduled;

import com.example.yourmemory.constant.ReminderStatus;
import com.example.yourmemory.model.jpa.ReminderPlan;
import com.example.yourmemory.model.jpa.ReminderPlanRepository;
import com.example.yourmemory.model.jpa.User;
import com.example.yourmemory.model.jpa.UserRepository;
import com.example.yourmemory.service.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.example.tgcommons.model.button.ButtonsDescription;
import org.example.tgcommons.model.wrapper.DeleteMessageWrap;
import org.example.tgcommons.model.wrapper.SendMessageWrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.tgcommons.constant.Constant.TextConstants.NEW_LINE;
import static org.example.tgcommons.utils.ButtonUtils.createVerticalColumnMenu;

@Service
@Slf4j
public class ReminderService {

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReminderPlanRepository reminderPlanRepository;

    @Scheduled(cron = "${cron.expression}")
//    @PostConstruct
    public void spam() {
        log.info("Scheduled старт");
        val reminderPlans = findReminderPlans();
        doReminder(scheduledMessageProcess(reminderPlans));
        updateReminders(reminderPlans);
    }

    private void doReminder(List<PartialBotApiMethod> answers) {
        for (val answer : answers) {
            try {
                if (answer instanceof BotApiMethod<? extends Serializable> botApiMethod) {
                    telegramBot.execute(botApiMethod);
                }
                if (answer instanceof SendDocument sendDocument) {
                    telegramBot.execute(sendDocument);
                }
                if (answer instanceof SendPhoto sendPhoto) {
                    telegramBot.execute(sendPhoto);
                }
            } catch (TelegramApiException e) {
                log.error("SCHEDULED Ошибка во время обработки сообщения: " + e.getMessage());
            }
        }
    }

    private List<ReminderPlan> findReminderPlans() {
        val findDateTimeStart = LocalDateTime.now();
        val reminderPlans = reminderPlanRepository.findAllByReminderStatus(ReminderStatus.OPEN);
        return reminderPlans.stream()
                .filter(reminderPlan -> Period.between(reminderPlan.getReminderDateTime().toLocalDate(), findDateTimeStart.toLocalDate()).isZero())
                .filter(reminderPlan -> ChronoUnit.HOURS.between(reminderPlan.getReminderDateTime(), findDateTimeStart) < 1)
                .collect(Collectors.toList());
    }

    public List<PartialBotApiMethod> scheduledMessageProcess(List<ReminderPlan> reminderPlans) {
        val answer = new ArrayList<PartialBotApiMethod>();
        for (ReminderPlan reminderPlan : reminderPlans) {
            val message = new StringBuilder(reminderPlan.getBirthdayCard().toString("Напоминанием о дне рождении!!!"));
            message.append(NEW_LINE).append("*Исполняется: *").append(reminderPlan.getTurnsYearsOld());
            answer.add(createMessage(reminderPlan.getBirthdayCard().getUser(), message.toString()));
        }
        return answer;
    }

    public void updateReminders(List<ReminderPlan> reminderPlans) {
        val updatedReminders = reminderPlans.stream()
                .map(reminderPlan -> {
                    reminderPlan.setReminderDateTime(reminderPlan.getReminderDateTime().plusYears(1));
                    return reminderPlan;
                })
                .collect(Collectors.toList());
        reminderPlanRepository.saveAll(updatedReminders);
    }

    //=======================================
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
        if (!update.hasCallbackQuery()) {
            return null;
        }
        val message = update.getCallbackQuery().getMessage();
        return DeleteMessageWrap.init()
                .setChatIdLong(message.getChatId())
                .setMessageId(message.getMessageId())
                .build().createMessage();
    }

    protected PartialBotApiMethod createMessage(User user, String message, ButtonsDescription buttonsDescription) {
        return createMessage(user, message, createVerticalColumnMenu(buttonsDescription));
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
}
