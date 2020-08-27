package com.myservice.mymarket.service.shared;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.StockQuote;
import com.myservice.mymarket.model.dto.cedro.MarketCedroData;
import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import com.myservice.mymarket.repository.MarketParamRepository;
import com.myservice.mymarket.repository.StockQuoteRepository;
import com.myservice.mymarket.service.TrackMarketUserService;
import com.myservice.mymarket.util.RestTemplateUtil;
import com.myservice.common.exceptions.BusinessException;
import com.myservice.common.exceptions.MessageException;
import com.myservice.common.util.DateUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.RoundingMode;
import java.util.*;

@Component
public class CedroIntegration {

    private static final Logger logger = LoggerFactory.getLogger(CedroIntegration.class);
    public static final String BVMF = "BVMF";

    @Autowired
    MarketParamRepository marketParamRepository;

    @Autowired
    StockQuoteRepository stockQuoteRepository;

    @Autowired
    SocketStockIntegration socketStockIntegration;

    //TODO melhorar logica onde está indo buscar de 100 em 100.
    public void syncQuoteByProduct(List<Stock> stocks) {
        if (stocks == null || stocks.isEmpty()) {
            logger.info("########## STOCK:  LISTA VAZIA ##############");
            return;
        }
        MarketParam urlLoginParam = marketParamRepository.findByCode(CodeParamEnum.URL_LOGIN_CEDRO_API);
        MarketParam userParam = marketParamRepository.findByCode(CodeParamEnum.USER_CEDRO_API);
        MarketParam passWordParam = marketParamRepository.findByCode(CodeParamEnum.PASSWORD_CEDRO_API);
        MarketParam urlQuoteParam = marketParamRepository.findByCode(CodeParamEnum.URL_GET_PRICE_CEDRO_API);
        String urlLoginCedro = urlLoginParam.getValue().replace("{0}", userParam.getValue()).replace("{1}", passWordParam.getValue());
        try {
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplateTrustStrategy();
            String json = restTemplate.postForObject(urlLoginCedro, null, String.class);
            boolean isLogged = Boolean.TRUE.toString().equalsIgnoreCase(json);
            ObjectMapper objectMapper = new ObjectMapper();
            List<StockQuote> stockQuotes = new ArrayList<>();

            if (!isLogged) {
                logger.info("## NÃO FOI POSSÍVEL FAZER LOGIN NA API CEDRO");
                return;
            }

            logger.info("## INICIO CONSULTA PREÇOS DAS AÇÕES");
            int i = 1;
            String stocksURL = "";
            Stock lastStock = stocks.get(stocks.size() - 1);
            for (Stock stock : stocks) {
                stocksURL = stocksURL + stock.getCode() + "/";

                if (i < 99 && !stock.getCode().equals(lastStock.getCode())) {
                    i++;
                    continue;
                }

                String urlStock = urlQuoteParam.getValue().replace("{0}", stocksURL);
                String jsonStock = restTemplate.getForObject(urlStock, String.class);
                List<MarketCedroData> marketCedroDataList = new ArrayList<>();

                if(i == 1){
                    MarketCedroData marketCedroData = objectMapper.readValue(jsonStock, MarketCedroData.class);
                    marketCedroDataList.add(marketCedroData);
                }else{
                    marketCedroDataList= objectMapper.readValue(jsonStock, objectMapper.getTypeFactory().constructCollectionType(List.class, MarketCedroData.class));
                }

                i = 1;
                stocksURL = "";

                if (marketCedroDataList == null || marketCedroDataList.isEmpty()) {
                    logger.info("########## NAO ENCONTROU INFORMAÇÕES DE COTAS ##############");
                    continue;
                }
                for (MarketCedroData marketCedroData : marketCedroDataList) {
                    if (marketCedroData.getMessageError() == null || marketCedroData.getMessageError().isBlank()) {
                        fillStockQuote(stockQuotes, marketCedroData);
                    } else {
                        logger.info("########## STOCK : " + stock.getCode() + " NÃO ENCONTROU  Registros ##############");
                    }
                }
            }
            logger.info("## FIM CONSULTA DE STOCK QUOTE");
            if (stockQuotes != null && !stockQuotes.isEmpty()) {
                logger.info("## SALVAR  OS DADOS DE PREÇOS, QUANTIDADE: " + stockQuotes.size());
                stockQuoteRepository.saveAll(stockQuotes);
            }
        } catch (Exception e) {
            logger.info("## ERRO AO FAZER CONSULTA DE STOCKS");
            logger.error("## ERRO AO FAZER CONSULTA DE STOCKS");
            e.printStackTrace();
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_SYNC_ERROR.getMessage());
        }
    }

    private void fillStockQuote(List<StockQuote> stockQuotes, MarketCedroData marketCedroData) {
        logger.info("########## STOCK : " + marketCedroData.getSymbol() + " OK ##############");
        StockQuote stockQuote = stockQuoteRepository.findByCodeStock(marketCedroData.getSymbol().toUpperCase());
        if (stockQuote == null) {
            stockQuote = new StockQuote();

        }
        if (marketCedroData.getDateTrade() != null && !marketCedroData.getDateTrade().isBlank()) {
            stockQuote.setDateQuote(DateUtil.formatBR(marketCedroData.getDateTrade(), "dd-MM-yyyy"));
        }
        stockQuote.setExchange(BVMF);
        stockQuote.setHigh(marketCedroData.getHigh());
        stockQuote.setLow(marketCedroData.getLow().setScale(2, RoundingMode.HALF_EVEN));
        stockQuote.setOpen(marketCedroData.getOpen().setScale(2, RoundingMode.HALF_EVEN));
        stockQuote.setPrice(marketCedroData.getLastTrade().setScale(2, RoundingMode.HALF_EVEN));
        stockQuote.setPriceVariationDay(marketCedroData.getChange().setScale(2, RoundingMode.HALF_EVEN));
        stockQuote.setCodeStock(marketCedroData.getSymbol().toUpperCase());
        stockQuote.setVolume(marketCedroData.getVolumeFinancier());
        stockQuotes.add(stockQuote);
        socketStockIntegration.broadcastStockQuote(stockQuote);
    }
}
