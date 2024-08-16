package com.apponex.eLibaryManagment.core.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum BusinessErrorCodes {
    NO_CODE(0, NOT_IMPLEMENTED,"No code"),
    INCORRECT_CURRENT_PASSWORD(300,BAD_REQUEST,"Current password is wrong"),
    NEW_PASSWORD_DOES_NOT_MATCH(301,BAD_REQUEST,"The password does not match"),
    ACCOUNT_LOCKED(302,FORBIDDEN,"User account is locked"),
    ACCOUNT_DISABLED(303,FORBIDDEN,"User account is disabled"),
    BAD_CREDENTIALS(303,FORBIDDEN, "Password or login is wrong")
    ;

    @Getter
    private final int code;
    @Getter
    private final HttpStatus status;
    @Getter
    private final String description;

    BusinessErrorCodes(int code, HttpStatus status, String description) {
        this.code = code;
        this.status = status;
        this.description = description;
    }
}
