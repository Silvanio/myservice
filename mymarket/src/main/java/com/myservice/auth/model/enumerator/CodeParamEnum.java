package com.myservice.auth.model.enumerator;

import lombok.Getter;

@Getter
public enum CodeParamEnum {

    URL_HISTORICAL_DATA_STOCK,
    URL_HISTORICAL_DATA_CRYPTO,
    TOKEN_HISTORICAL_DATA_STOCK;

    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }
        return names;
    }

}