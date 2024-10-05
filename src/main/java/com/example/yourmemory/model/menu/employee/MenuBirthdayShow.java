package com.example.yourmemory.model.menu.employee;

import com.example.yourmemory.model.jpa.BirthdayCardRepository;
import com.example.yourmemory.model.jpa.User;
import com.example.yourmemory.model.menu.base.Menu;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.yourmemory.constant.Constant.Command.COMMAND_BIRTHDAY_SHOW;
import static org.example.tgcommons.constant.Constant.TextConstants.EMPTY;
import static org.example.tgcommons.constant.Constant.TextConstants.NEW_LINE;

@Component(COMMAND_BIRTHDAY_SHOW)
@Slf4j
public class MenuBirthdayShow extends Menu {

    @Override
    public String getMenuComand() {
        return COMMAND_BIRTHDAY_SHOW;
    }

    @Autowired
    private BirthdayCardRepository birthdayCardRepository;

    @Override
    public List<PartialBotApiMethod> menuRun(User user, Update update) {
        try {
            return switch (stateService.getState(user)) {
                case FREE -> freelogic(user, update);
                default -> errorMessageDefault(update);
            };
        } catch (Exception ex) {
            log.error(ex.toString());
            return errorMessage(update, ex.toString());
        }
    }

    private List<PartialBotApiMethod> freelogic(User user, Update update) {
        val birthdayCards = birthdayCardRepository.findAllByUserOrderByBirthdayAsc(user);
        val answer = birthdayCards.stream()
                .map(birthdayCard -> birthdayCard.toString(EMPTY))
                .collect(Collectors.joining(NEW_LINE));
        return createMessageList(user, "Сохраненные дни рождения:" + answer);
    }

    @Override
    public String getDescription() {
        return getMenuComand();
    }

}
