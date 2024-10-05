package org.example.tgcommons.utils;

import lombok.val;

import java.text.NumberFormat;
import java.util.Locale;

import static org.example.tgcommons.constant.Constant.TextConstants.EMPTY;

public class NumberConverter {

    public static final String LANGUAGE = "ru";
    public static final Locale locale = new Locale(LANGUAGE);

    private NumberConverter() {
    }

    public static String formatDouble(Double value) {
        val formatter = NumberFormat.getInstance(locale);
        return formatter.format(value);
    }

    public static Integer convertToIntegerOrNull(String value) {
        try {
            if (value.equals(EMPTY)) {
                return null;
            }
            return Integer.parseInt(value);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Double convertToDoubleOrNull(String value) {
        try {
            if (value.equals(EMPTY)) {
                return null;
            }
            return Double.parseDouble(value);
        } catch (Exception ex) {
            return null;
        }
    }
}
