package com.example.yourmemory.config;

import com.example.yourmemory.enums.UserRole;
import lombok.Getter;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.yourmemory.constant.Constant.Command.*;
import static com.example.yourmemory.enums.UserRole.*;
import static org.example.tgcommons.constant.Constant.Command.COMMAND_DEFAULT;
import static org.example.tgcommons.constant.Constant.Command.COMMAND_START;
import static org.example.tgcommons.constant.Constant.ConfigParams.*;

@Configuration
@EnableScheduling
@Getter
@PropertySource(PROPERTY_SOURCE)
public class BotConfig {

    @Value(BOT_VERSION)
    String botVersion;

    @Value(BOT_USERNAME)
    String botUserName;

    @Value(BOT_TOKEN)
    String botToken;

    @Bean
    public Map<UserRole, List<String>> roleAccess() {
        val roleAccess = new HashMap<UserRole, List<String>>();
        val roleBase = List.of(COMMAND_DEFAULT, COMMAND_START, COMMAND_BIRTHDAY_ADD, COMMAND_BIRTHDAY_SHOW, COMMAND_BIRTHDAY_DELETE);
        roleAccess.put(BLOCKED, List.of(COMMAND_DEFAULT, COMMAND_START));
        roleAccess.put(EMPLOYEE, roleBase);
        roleAccess.put(MANAGER, roleBase);
        roleAccess.put(ADMIN, roleBase);
        return roleAccess;
    }

}
