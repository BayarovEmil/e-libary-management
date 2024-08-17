package com.apponex.eLibaryManagment.core.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),
    SELLER_READ("SELLER:read"),
    SELLER_UPDATE("SELLER:update"),
    SELLER_CREATE("SELLER:create"),
    SELLER_DELETE("SELLER:delete")
    ;

    @Getter
    private final String permission;
}
