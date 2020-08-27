package com.myservice.mymarket.service;

public interface SyncService {
    void syncHistoricalDataStock();

    void syncQuoteStock();

    void refreshQuote(String codeStock);

    void syncQuoteCrypto();

    void syncHistoricalIbovespa();
}