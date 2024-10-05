package com.example.yourmemory.enums;

public enum UserRole {

    BLOCKED("Заблокирован"),
    EMPLOYEE("Пользователь"),
    MANAGER("Менеджер"),
    ADMIN("Администратор");

    private String title;

    UserRole(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
