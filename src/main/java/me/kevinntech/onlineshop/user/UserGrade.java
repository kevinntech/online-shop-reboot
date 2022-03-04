package me.kevinntech.onlineshop.user;

public enum UserGrade {
    ADMIN("관리자"), USER("사용자");

    private String description;

    UserGrade(String description) {
        this.description = description;
    }
}
