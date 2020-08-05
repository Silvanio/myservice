package com.myservice.auth.service.impl;

import com.myservice.auth.model.MarketParam;
import com.myservice.auth.model.Product;
import com.myservice.auth.model.SyncLog;
import com.myservice.auth.model.enumerator.CodeParamEnum;
import com.myservice.auth.repository.*;
import com.myservice.auth.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class SyncServiceImpl implements SyncService {

    private static final Logger logger = LoggerFactory.getLogger(SyncServiceImpl.class);

    @Autowired
    SyncLogRepository syncLogRepository;

    @Autowired
    MarketParamRepository marketParamRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockHistoricalDataRepository stockHistoricalDataRepository;

    @Autowired
    StockQuoteRepository stockQuoteRepository;

    @Override
    public void syncHistoricalDataStock() {
        logger.info("## START SYNC HISTORICAL DATA");
        SyncLog syncLog = getSyncLogHistoricalData();

        MarketParam url = marketParamRepository.findByCode(CodeParamEnum.URL_HISTORICAL_DATA_STOCK);
        MarketParam token = marketParamRepository.findByCode(CodeParamEnum.TOKEN_HISTORICAL_DATA_STOCK);

        Product action = productRepository.findByCode("STOCK");
        Product etf = productRepository.findByCode("ETF");
        Product fii = productRepository.findByCode("FII");


        syncLog.setFinish(new Date());
        syncLog.setSuccess(true);
        syncLogRepository.save(syncLog);
        logger.info("## FIM SYNC HISTORICAL DATA");
    }

    @Override
    public void syncQuoteStock() {
        logger.info("## START SYNC QUOTE STOCK");
        SyncLog syncLog = getSyncLogQuoteStock();


        syncLog.setFinish(new Date());
        syncLogRepository.save(syncLog);
        syncLog.setSuccess(true);
        logger.info("## FIM SYNC QUOTE STOCK");
    }

    private SyncLog getSyncLogHistoricalData() {
        SyncLog syncLog = new SyncLog();
        syncLog.setAction("syncHistoricalDataStock");
        syncLog.setStart(new Date());
        syncLog = syncLogRepository.save(syncLog);
        return syncLog;
    }

    private SyncLog getSyncLogQuoteStock() {
        SyncLog syncLog = new SyncLog();
        syncLog.setAction("syncQuoteStock");
        syncLog.setStart(new Date());
        syncLog = syncLogRepository.save(syncLog);
        return syncLog;
    }
}