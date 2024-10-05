package org.example.tgcommons.utils;

import lombok.val;

import static org.example.tgcommons.constant.Constant.TextConstants.STAR;

public class MessageUtils {

    private MessageUtils() {
    }

    public static String prepareBold(final String text) {
        val boldText = new StringBuilder();
        boldText.append(STAR).append(text).append(STAR);
        return boldText.toString();
    }
}
