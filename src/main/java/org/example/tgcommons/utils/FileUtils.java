package org.example.tgcommons.utils;

import static org.example.tgcommons.constant.Constant.FileOperation.USER_DIR;
import static org.example.tgcommons.constant.Constant.TextConstants.SHIELD;

public class FileUtils {

    private FileUtils() {
    }

    public static String getCurrentPath() {
        return System.getProperty(USER_DIR) + SHIELD;
    }
}
