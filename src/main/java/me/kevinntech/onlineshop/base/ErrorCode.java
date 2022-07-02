package me.kevinntech.onlineshop.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
    ENTITY_NOT_FOUND(400, "C003", " Entity Not Found"),
    DATA_ACCESS_ERROR(500, "C004", "Data Access Error"),
    INTERNAL_SERVER_ERROR(500, "C005", "Server Error"),

    // User
    EMAIL_DUPLICATION(400, "U001", "Email is Duplication"),
    LOGIN_INPUT_INVALID(400, "U002", "Login input is invalid");

    private final int status;
    private final String code;
    private final String message;

}
