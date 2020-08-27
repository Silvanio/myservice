package com.myservice.mymarket.service.shared;

import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.StockHistoricalData;
import com.myservice.mymarket.repository.StockHistoricalDataRepository;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.net.ssl.*;
import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class YahooIntegration {

    private static final Logger logger = LoggerFactory.getLogger(YahooIntegration.class);

    @Autowired
    StockHistoricalDataRepository stockHistoricalDataRepository;

    public void syncHistoricalQuote(Stock stock) {
        this.syncHistoricalQuote(stock, null, null);
    }

    public void syncHistoricalQuote(Stock stock, String exchange) {
        this.syncHistoricalQuote(stock, exchange, null);
    }

    public void syncHistoricalQuote(String changeStockCode, Stock stock) {
        this.syncHistoricalQuote(stock, null, changeStockCode);
    }

    private void syncHistoricalQuote(Stock stock, String exchange, String changeStockCode) {
        try {
            trustValidate();
            List<HistoricalQuote> historicalList = null;
            List<StockHistoricalData> historicalDataList = new ArrayList<>();

            String stockCode = stock.getCode();
            if (exchange != null && !exchange.isBlank()) {
                stockCode = stockCode + "." + exchange;
            }
            if (changeStockCode != null && !changeStockCode.isBlank()) {
                stockCode = changeStockCode;
            }

            Calendar from = new GregorianCalendar();
            from.setTime(getLastDayByHistoricalStock(stock));
            from.add(Calendar.DAY_OF_MONTH,2);

            Calendar to = new GregorianCalendar();
            to.setTime(new Date());

            yahoofinance.Stock stockYahoo = YahooFinance.get(stockCode, from, to, Interval.DAILY);
            if (stockYahoo != null) {
                historicalList = stockYahoo.getHistory();
            }

            if (historicalList != null && !historicalList.isEmpty()) {
                logger.info("########## STOCK : " + stock.getCode() + " ENCONTROU: " + historicalList.size() + " Registros ##############");
                for (HistoricalQuote data : historicalList) {
                    StockHistoricalData historicalData = new StockHistoricalData();
                    historicalData.setClose(data.getClose());
                    historicalData.setCodeStock(stock.getCode());
                    if (data.getDate() != null) {
                        Calendar dateQuoteBr = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
                        dateQuoteBr.set(data.getDate() .get(Calendar.YEAR),data.getDate() .get(Calendar.MONTH),data.getDate() .get(Calendar.DAY_OF_MONTH));
                        historicalData.setDateQuote(dateQuoteBr.getTime());
                    }
                    historicalData.setExchange(stock.getExchange());
                    historicalData.setHigh(data.getHigh());
                    historicalData.setLow(data.getLow());
                    historicalData.setOpen(data.getOpen());
                    if (data.getVolume() != null) {
                        historicalData.setVolume(new BigDecimal(data.getVolume()));
                    }
                    historicalDataList.add(historicalData);
                }
            } else {
                logger.info("########## STOCK : " + stock.getCode() + " NÃO ENCONTROU  Registros ##############");
            }

            stockHistoricalDataRepository.saveAll(historicalDataList);
        } catch (Exception e) {
            logger.info("########## STOCK : " + stock.getCode() + " ERRO AO OBTER DADOS DO INDICE BOVESPA ##############");
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_SYNC_ERROR.getMessage());
        }
    }

    private Date getLastDayByHistoricalStock(Stock stock) {
        Date maxData = stockHistoricalDataRepository.getMaxDateQuoteByCodeStock(stock.getCode());
        if (maxData == null) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate now = LocalDate.now();
            now = now.minusYears(10);
            maxData = Date.from(now.atStartOfDay(defaultZoneId).toInstant());
        }
        return maxData;
    }

    private void trustValidate() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }
}
