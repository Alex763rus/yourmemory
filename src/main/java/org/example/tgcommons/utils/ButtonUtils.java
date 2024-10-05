package org.example.tgcommons.utils;

import lombok.val;
import org.example.tgcommons.model.button.ButtonsDescription;
import org.example.tgcommons.model.button.CalendarDescription;
import org.example.tgcommons.model.button.Day;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

import static java.util.Calendar.LONG_STANDALONE;
import static org.example.tgcommons.constant.Constant.Calendar.*;
import static org.example.tgcommons.constant.Constant.TextConstants.SPACE;

public class ButtonUtils {

    public static InlineKeyboardMarkup createVerticalColumnMenu(final ButtonsDescription buttonsDescription) {
        val inlineKeyboardMarkup = new InlineKeyboardMarkup();
        val rows = new ArrayList<List<InlineKeyboardButton>>();

        int indexMenu = 1;
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        val buttons = buttonsDescription.getButtons();
        for (val button : buttons) {
            rowInline.add(createButton(button.getKey(), button.getValue(), button.getLink()));
            if (indexMenu % buttonsDescription.getCountColumn() == 0) {
                rows.add(rowInline);
                rowInline = new ArrayList<>();
            }
            ++indexMenu;
        }
        rows.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup createCalendar(Calendar calendar, Map<Integer, String> markedDays) {
        val days = new ArrayList<Day>();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        val dayOfWeekStart = dayOfWeek == 0 ? 7 : dayOfWeek;

        val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        val selectedMonth = calendar.getDisplayName(Calendar.MONTH, LONG_STANDALONE, new Locale("ru"));
        val year = calendar.get(Calendar.YEAR);

        calendar.add(Calendar.MONTH, 1);
        val nextMonth = calendar.getDisplayName(Calendar.MONTH, LONG_STANDALONE, new Locale("ru"));
        calendar.add(Calendar.MONTH, -2);
        val prevMonth = calendar.getDisplayName(Calendar.MONTH, LONG_STANDALONE, new Locale("ru"));
        calendar.add(Calendar.MONTH, 1);
        for (int iDay = 1; iDay <= maxDay; iDay++) {
            val dayKey = String.valueOf(iDay);
            val dayValue = markedDays.getOrDefault(iDay, dayKey);
            days.add(Day.init().setKey(dayKey).setValue(dayValue).build());
        }

        return createCalendar(CalendarDescription.init()
                .setSelectedMonth(selectedMonth + " " + year)
                .setPrevMonth("<< " + prevMonth)
                .setNextMonth(nextMonth + " >>")
                .setDayOfWeekStart(dayOfWeekStart)
                .setDays(days)
                .build());
    }
    private static InlineKeyboardMarkup createCalendar(CalendarDescription calendarDescription) {
        val inlineKeyboardMarkup = new InlineKeyboardMarkup();
        val rows = new ArrayList<List<InlineKeyboardButton>>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(createButton(SELECTED_MONTH, calendarDescription.getSelectedMonth()));
        rows.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(createButton("Monday", "Пн"));
        rowInline.add(createButton("Tuesday", "Вт"));
        rowInline.add(createButton("Wednesday", "Ср"));
        rowInline.add(createButton("Thursday", "Чт"));
        rowInline.add(createButton("Friday", "Пт"));
        rowInline.add(createButton("Saturday", "Сб"));
        rowInline.add(createButton("Sunday", "Вс"));
        rows.add(rowInline);

        rowInline = new ArrayList<>();
        int dayCounter = 1;
        val days = calendarDescription.getDays();

        while (dayCounter < calendarDescription.getDayOfWeekStart()) {
            rowInline.add(createButton(EMPTY_DAY + dayCounter, SPACE));
            ++dayCounter;
        }

        for (Day day : days) {
            rowInline.add(createButton(day.getKey(), day.getValue()));
            if (dayCounter % 7 == 0) {
                rows.add(rowInline);
                rowInline = new ArrayList<>();
            }
            ++dayCounter;
        }

        while (dayCounter % 7 != 1) {
            rowInline.add(createButton(EMPTY_DAY + dayCounter, SPACE));
            ++dayCounter;
        }
        rows.add(rowInline);
        rowInline = new ArrayList<>();

        rowInline.add(createButton(PREV_MONTH, calendarDescription.getPrevMonth()));
        rowInline.add(createButton(NEXT_MONTH, calendarDescription.getNextMonth()));
        rows.add(rowInline);

        rowInline = new ArrayList<>();
        rowInline.add(createButton(MAIN_MENU, "Главное меню"));
        rows.add(rowInline);
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardButton createButton(String key, String value) {
        return createButton(key, value, null);
    }

    private static InlineKeyboardButton createButton(String key, String value, String link) {
        val btn = new InlineKeyboardButton();
        btn.setCallbackData(key);
        btn.setText(value);
        if (link != null) {
            btn.setUrl(link);
        }
        return btn;
    }
}
