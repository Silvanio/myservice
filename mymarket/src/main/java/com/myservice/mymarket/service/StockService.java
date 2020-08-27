package com.myservice.mymarket.service;

import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.common.service.IService;

import java.util.List;

public interface StockService extends IService<Long, Stock, StockDTO> {

    void refreshQuote(String codeStock);

    List<Stock> findByCodeStarts(String codeStock);
}
