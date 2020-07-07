package com.myservice.common.domain;

import lombok.Getter;

@Getter
public enum Authorities {

    AUTH_CLIENT_ADMIN,
    AUTH_ADMIN;

    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }
        return names;
    }

}