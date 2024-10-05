package com.example.yourmemory.constant;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Constant {

    public static final String APP_NAME = "YourMemory";

    @NoArgsConstructor(access = PRIVATE)
    public static final class Command {

        public static final String COMMAND_SERVICE_1 = "/equipment_design";

        public static final String COMMAND_BIRTHDAY_SHOW = "/birthday_show";
        public static final String COMMAND_BIRTHDAY_ADD = "/birthday_add";
        public static final String COMMAND_BIRTHDAY_DELETE = "/birthday_delete";

    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class MenuChooseTime {

        public static final String BACK = "back";
    }

}
