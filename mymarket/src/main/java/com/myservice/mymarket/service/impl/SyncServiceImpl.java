package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.*;
import com.myservice.mymarket.repository.*;
import com.myservice.mymarket.service.SyncService;
import com.myservice.mymarket.service.shared.CedroIntegration;
import com.myservice.mymarket.service.shared.MarketStackIntegration;
import com.myservice.mymarket.service.shared.MercadoBTCIntegration;
import com.myservice.mymarket.service.shared.YahooIntegration;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SyncServiceImpl implements SyncService {

    private static final Logger logger = LoggerFactory.getLogger(SyncServiceImpl.class);

    @Autowired
    SyncLogRepository syncLogRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StockRepository stockRepository;

    @Autowired
    CedroIntegration cedroIntegration;

    @Autowired
    MercadoBTCIntegration mercadoBTCIntegration;

    @Autowired
    MarketStackIntegration marketStackIntegration;

    @Autowired
    YahooIntegration yahooIntegration;

    @Override
    @Async
    public void syncHistoricalIbovespa() {
        logger.info("## START SYNC QUOTE IBovespa");
        SyncLog syncLog = getSyncLogQuoteCrypto();
        try {
            yahooIntegration.syncHistoricalQuote("^BVSP",stockRepository.findByCode("IBOV"));
            syncLog.setFinish(new Date());
            syncLogRepository.save(syncLog);
            syncLog.setSuccess(true);
            logger.info("## FIM SYNC QUOTE IBovespa");
        } catch (Exception e) {
            syncLog.setFinish(new Date());
            syncLog.setSuccess(false);
            syncLogRepository.save(syncLog);
            logger.info("## ERRO - FINALIZOU COM ERRO-  SYNC IBovespa");
        }
    }

    @Override
    @Async
    public void syncQuoteCrypto() {
        logger.info("## START SYNC QUOTE CRYPTO");
        SyncLog syncLog = getSyncLogQuoteCrypto();
        Product productCRIPTO = productRepository.findByCode("CRIPTO");
        try {
            mercadoBTCIntegration.syncQuoteCrypto(stockRepository.findByProduct(productCRIPTO));
            syncLog.setFinish(new Date());
            syncLogRepository.save(syncLog);
            syncLog.setSuccess(true);
            logger.info("## FIM SYNC QUOTE CRYPTO");
        } catch (Exception e) {
            syncLog.setFinish(new Date());
            syncLog.setSuccess(false);
            syncLogRepository.save(syncLog);
            logger.info("## ERRO - FINALIZOU COM ERRO-  SYNC CRYPTO");
        }
    }


    @Override
    @Async
    public void syncQuoteStock() {
        logger.info("## START SYNC QUOTE STOCK");
        SyncLog syncLog = getSyncLogQuoteStock();

        Product productStock = productRepository.findByCode("STOCK");
        Product productETF = productRepository.findByCode("ETF");
        Product productFII = productRepository.findByCode("FII");
        Product productIndex = productRepository.findByCode("INDEX");
        Product productFounds = productRepository.findByCode("FUNDS");
        Product productBDR = productRepository.findByCode("BDR");

        try {

            List<Stock> stocks = new ArrayList<>();
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productStock, true));
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productETF, true));
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productFII, true));
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productIndex, true));
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productFounds, true));
            stocks.addAll(stockRepository.findByProductAndRefreshQuote(productBDR, true));

            cedroIntegration.syncQuoteByProduct(stocks);

            syncLog.setFinish(new Date());
            syncLogRepository.save(syncLog);
            syncLog.setSuccess(true);
            logger.info("## FIM SYNC QUOTE STOCK");

        } catch (Exception e) {
            syncLog.setFinish(new Date());
            syncLog.setSuccess(false);
            syncLogRepository.save(syncLog);
            logger.info("## ERRO - FINALIZOU COM ERRO-  SYNC HISTORICAL DATA");
        }
    }


    @Override
    @Async
    public void syncHistoricalDataStock() {
        logger.info("## START SYNC HISTORICAL DATA");
        SyncLog syncLog = getSyncLogHistoricalData();

        Product productStock = productRepository.findByCode("STOCK");
        Product productETF = productRepository.findByCode("ETF");
        Product productFII = productRepository.findByCode("FII");
        Product productIndex = productRepository.findByCode("INDEX");
        Product productFounds = productRepository.findByCode("FUNDS");
        Product productBDR = productRepository.findByCode("BDR");

        try {
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productStock,true));
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productETF,true));
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productFII,true));
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productIndex,true));
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productFounds,true));
            marketStackIntegration.syncHistoricalByProduct(stockRepository.findByProductAndRefreshQuote(productBDR,true));
            syncLog.setFinish(new Date());
            syncLog.setSuccess(true);
            syncLogRepository.save(syncLog);
            logger.info("## FIM SYNC HISTORICAL DATA");
        } catch (Exception e) {
            syncLog.setFinish(new Date());
            syncLog.setSuccess(false);
            syncLogRepository.save(syncLog);
            logger.info("## ERRO - FINALIZOU COM ERRO-  SYNC HISTORICAL DATA");
        }
    }

    @Override
    public void refreshQuote(String codeStock) {
        Stock stock = stockRepository.findByCode(codeStock);
        if (stock == null) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_INFO_NOT_FOUND.getMessage());
        }
        List<Stock> stocks = new ArrayList<>();
        stocks.add(stock);
        if (stock.getProduct().getCode().equalsIgnoreCase("CRIPTO")) {
            mercadoBTCIntegration.syncQuoteCrypto(stocks);
            return;
        } else if (stock.isStockRefreshQuote()) {
            cedroIntegration.syncQuoteByProduct(stocks);
        }
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

    private SyncLog getSyncLogQuoteCrypto() {
        SyncLog syncLog = new SyncLog();
        syncLog.setAction("syncQuoteCrypto");
        syncLog.setStart(new Date());
        syncLog = syncLogRepository.save(syncLog);
        return syncLog;
    }
}