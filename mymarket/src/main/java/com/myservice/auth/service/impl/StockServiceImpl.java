package com.myservice.auth.service.impl;

import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.repository.StockRepository;
import com.myservice.auth.service.StockService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StockServiceImpl extends MyService<Long, Stock, StockDTO> implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    public StockRepository getRepository() {
        return stockRepository;
    }
}
