package org.example.tgcommons.model.button;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class CalendarDescription {

    private final String selectedMonth;

    private final int dayOfWeekStart;

    private final String nextMonth;

    private final String prevMonth;

    private final List<Day> days;

}
