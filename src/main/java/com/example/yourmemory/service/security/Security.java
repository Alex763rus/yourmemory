package com.example.yourmemory.service.security;

import com.example.yourmemory.enums.UserRole;
import com.example.yourmemory.model.jpa.User;
import com.example.yourmemory.model.menu.MenuActivity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.example.tgcommons.constant.Constant.Command.COMMAND_DEFAULT;
import static org.example.tgcommons.constant.Constant.Command.COMMAND_START;


@Component
@Getter
@AllArgsConstructor
public class Security {

    private final Map<UserRole, List<String>> roleAccess;

    private final Map<String, MenuActivity> mainMenu;

    public MenuActivity getMenuActivity(String commandName) {
        return mainMenu.getOrDefault(commandName, null);
    }

    public boolean checkAccess(User user, String menuComand) {
        if (menuComand.equals(COMMAND_START) || menuComand.equals(COMMAND_DEFAULT)) {
            return true;
        }
        return roleAccess.get(user.getUserRole()).contains(menuComand);
    }
}
