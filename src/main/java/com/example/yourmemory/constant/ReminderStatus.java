package com.example.yourmemory.constant;

public enum ReminderStatus {

    OPEN(""),

    COMPLETED(""),

    CANCELED("");

    private String reminderStatus;

    ReminderStatus(String reminderStatus) {
        this.reminderStatus = reminderStatus;
    }

    public String getReminderStatus() {
        return reminderStatus;
    }
}
