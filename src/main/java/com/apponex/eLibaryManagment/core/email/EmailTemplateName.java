package com.apponex.eLibaryManagment.core.email;

import lombok.Getter;

public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account")
    ;
    @Getter
    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
