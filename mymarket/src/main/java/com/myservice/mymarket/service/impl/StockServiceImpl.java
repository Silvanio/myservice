package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.dto.StockDTO;
import com.myservice.mymarket.repository.StockRepository;
import com.myservice.mymarket.service.StockService;
import com.myservice.mymarket.service.SyncService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StockServiceImpl extends MyService<Long, Stock, StockDTO> implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    SyncService syncService;

    @Override
    public StockRepository getRepository() {
        return stockRepository;
    }

    @Override
    public void refreshQuote(String codeStock) {
        syncService.refreshQuote(codeStock);
    }
    @Override
    public List<Stock> findByCodeStarts(String codeStock) {
        return stockRepository.findTop20ByCodeStartsWithIgnoreCase(codeStock);
    }
}
