package com.myservice.mymarket.model.enumerator;

import lombok.Getter;

@Getter
public enum CodeParamEnum {

    URL_HISTORICAL_DATA_STOCK,
    URL_HISTORICAL_DATA_CRYPTO,
    TOKEN_HISTORICAL_DATA_STOCK,
    URL_LOGIN_CEDRO_API,
    USER_CEDRO_API,
    PASSWORD_CEDRO_API,
    URL_GET_PRICE_CEDRO_API,
    URL_GET_PRICE_CRYPTO_MERCADO_BTC,
    B3_OPEN_MARKET,
    B3_CLOSE_MARKET;


    public static String[] names() {
        String[] names = new String[values().length];
        for (int index = 0; index < values().length; index++) {
            names[index] = values()[index].name();
        }
        return names;
    }

}