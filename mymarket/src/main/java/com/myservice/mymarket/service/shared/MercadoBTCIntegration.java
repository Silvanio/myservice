package com.myservice.mymarket.service.shared;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.StockQuote;
import com.myservice.mymarket.model.dto.mercadobtc.MarketMercadoBTC;
import com.myservice.mymarket.model.dto.mercadobtc.TickerCryptoDTO;
import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import com.myservice.mymarket.repository.MarketParamRepository;
import com.myservice.mymarket.repository.StockQuoteRepository;
import com.myservice.mymarket.util.RestTemplateUtil;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MercadoBTCIntegration {

    private static final Logger logger = LoggerFactory.getLogger(MercadoBTCIntegration.class);

    @Autowired
    MarketParamRepository marketParamRepository;

    @Autowired
    StockQuoteRepository stockQuoteRepository;

    @Autowired
    SocketStockIntegration socketStockIntegration;

    public void syncQuoteCrypto(List<Stock> stocks) {
        MarketParam urlQuoteParam = marketParamRepository.findByCode(CodeParamEnum.URL_GET_PRICE_CRYPTO_MERCADO_BTC);
        try {
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplateTrustStrategy();
            ObjectMapper objectMapper = new ObjectMapper();
            List<StockQuote> StockQuoteList = new ArrayList<>();

            for (Stock stock : stocks) {
                String urlPriceMercadoBTC = urlQuoteParam.getValue().replace("{0}", stock.getCode());
                String json = restTemplate.getForObject(urlPriceMercadoBTC, String.class);
                MarketMercadoBTC marketMercadoBTC = objectMapper.readValue(json, MarketMercadoBTC.class);
                if (marketMercadoBTC != null && marketMercadoBTC.getTicker() != null) {
                    TickerCryptoDTO ticker = marketMercadoBTC.getTicker();
                    logger.info("########## CRYPTO : " + stock.getCode() + " OK ##############");
                    StockQuote stockQuote = stockQuoteRepository.findByCodeStock(stock.getCode());
                    if (stockQuote == null) {
                        stockQuote = new StockQuote();
                    }
                    if (ticker.getDate() != null) {
                        //Epoch & Unix Timestamp
                        stockQuote.setDateQuote(new Date(ticker.getDate() * 1000));
                    }
                    stockQuote.setExchange(stock.getExchange());
                    stockQuote.setHigh(ticker.getHigh());
                    stockQuote.setLow(ticker.getLow());
                    stockQuote.setOpen(ticker.getOpen());
                    stockQuote.setPrice(ticker.getLast());
                    BigDecimal variation = ticker.getLast().subtract(ticker.getOpen()).setScale(4, RoundingMode.HALF_EVEN);
                    BigDecimal low = ticker.getLow().setScale(4, RoundingMode.HALF_EVEN);
                    BigDecimal result = variation.divide(low, MathContext.DECIMAL128).setScale(4, RoundingMode.HALF_EVEN);
                    BigDecimal percent = result.multiply(new BigDecimal(100)).setScale(2, RoundingMode.HALF_EVEN);
                    stockQuote.setPriceVariationDay(percent);
                    stockQuote.setCodeStock(stock.getCode());
                    stockQuote.setVolume(ticker.getVol());
                    StockQuoteList.add(stockQuote);
                    socketStockIntegration.broadcastStockQuote(stockQuote);
                } else {
                    logger.info("########## CRYPTO : " + stock.getCode() + " NÃO ENCONTROU  Registros ##############");
                }
            }

            logger.info("## FIM CONSULTA DE COTAÇÃO CRIPTO");
            logger.info("## SALVAR OS HISTÍCOS, QUANTIDADE: " + StockQuoteList.size());
            stockQuoteRepository.saveAll(StockQuoteList);
        } catch (Exception e) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_SYNC_ERROR.getMessage());
        }
    }
}
