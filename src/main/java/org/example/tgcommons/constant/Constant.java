package org.example.tgcommons.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Constant {

    @NoArgsConstructor(access = PRIVATE)
    public static final class ConfigParams {
        public static final String PROPERTY_SOURCE = "application.properties";
        public static final String BOT_VERSION = "${bot.version}";
        public static final String BOT_USERNAME = "${bot.username}";
        public static final String BOT_TOKEN = "${bot.token}";
        public static final String ADMIN_CHAT_ID = "${admin.chatid}";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Command {
        public static final String COMMAND_DEFAULT = "/default";
        public static final String COMMAND_START = "/start";
        public static final String COMMAND_BACK = "/back";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class FileOperation {
        public static final String FILE_STORAGE_URI = "https://api.telegram.org/file/bot{bot.token}/{filePath}";
        public static final String FILE_INFO_URI = "https://api.telegram.org/bot{bot.token}/getFile?file_id={fileId}";
        public static final String USER_DIR = "user.dir";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class TextConstants {
        public static final String SHIELD = "\\";
        public static final String EMPTY = "";
        public static final String STAR = "*";
        public static final String NEW_LINE = "\n";
        public static final String SPACE = " ";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class MessageConstants {
        public static final String DEFAULT_TEXT_ERROR = "Ошибка! Команда не найдена";
    }


    @NoArgsConstructor(access = PRIVATE)
    public static final class Telegramm {
        public static final String PARSE_MODE = "Markdown";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Calendar {

        public static final String SELECTED_MONTH = "SelectedMonth";
        public static final String PREV_MONTH = "PrevMonth";
        public static final String NEXT_MONTH = "NextMonth";
        public static final String MAIN_MENU = "MainMenu";
        public static final String EMPTY_DAY = "EmptyDay";

    }
}
