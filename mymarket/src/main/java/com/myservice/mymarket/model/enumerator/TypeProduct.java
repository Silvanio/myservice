package com.myservice.mymarket.model.enumerator;

import lombok.Getter;

@Getter
public enum TypeProduct {

    FIXED,
    VARIABLE;

    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }
        return names;
    }

}