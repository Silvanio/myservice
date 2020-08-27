package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import com.myservice.mymarket.repository.MarketParamRepository;
import com.myservice.mymarket.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class RefreshQuoteScheduler {
    private static final Logger log = LoggerFactory.getLogger(RefreshQuoteScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    MarketParamRepository marketParamRepository;

    @Autowired
    SyncService syncService;

    @Scheduled(cron = "0 */1 * * * MON-FRI")
    public void refreshStockB3() {
        log.info("##### SCHEDULE Refresh STOCK B3:", dateFormat.format(new Date()));
        Calendar now  = Calendar.getInstance();
        MarketParam openMarket = marketParamRepository.findByCode(CodeParamEnum.B3_OPEN_MARKET);
        MarketParam closeMarket = marketParamRepository.findByCode(CodeParamEnum.B3_CLOSE_MARKET);
        int hourNow = now.get(Calendar.HOUR_OF_DAY);
        int hourOpen = Integer.parseInt(openMarket.getValue());
        int hourClose = Integer.parseInt(closeMarket.getValue());
        if(hourNow >= hourOpen && hourNow <= hourClose){
            syncService.syncQuoteStock();
        }else{
            log.info("##### B3 IS CLOSED", dateFormat.format(new Date()));
        }
    }

    @Scheduled(cron = "0/50 * * * * *")
    public void refreshCrypto() {
        log.info("##### SCHEDULE Refresh CRYPTO:", dateFormat.format(new Date()));
        syncService.syncQuoteCrypto();
    }
}
