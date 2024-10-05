package org.example.tgcommons.enums;

public enum Emoji {

    BLUSH(":blush:"),
    ARROW_UP(":arrow_up:"),
    ARROW_DOWN(":arrow_down:"),
    SHOPPING_TROLLEY(":shopping_trolley:"),
    LOCK(":lock:"),
    UNLOCK(":unlock:"),
    QUESTION(":question:"),
    MOYAI(":moyai:"),
    MONEY_BAG(":moneybag:"),
    WARNING(":warning:"),
    OK_HAND(":ok_hand:"),
    SIGNAL_STRENGTH(":signal_strength:"),
    NO_MOBILE_PHONES(":no_mobile_phones:"),
    ROBOT_FACE(":robot_face:"),
    WHITE_CHECK_MARK(":white_check_mark:"),
    BLACK_SQUARE_BUTTON(":black_square_button:");

    private String code;

    Emoji(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
