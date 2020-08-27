package com.myservice.mymarket.service.shared;

import com.myservice.mymarket.model.MarketParam;
import com.myservice.mymarket.model.Stock;
import com.myservice.mymarket.model.StockHistoricalData;
import com.myservice.mymarket.model.dto.marktstack.MarketStackDTO;
import com.myservice.mymarket.model.dto.marktstack.MarketStackData;
import com.myservice.mymarket.model.enumerator.CodeParamEnum;
import com.myservice.mymarket.repository.MarketParamRepository;
import com.myservice.mymarket.repository.StockHistoricalDataRepository;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Component
public class MarketStackIntegration {

    private static final Logger logger = LoggerFactory.getLogger(MarketStackIntegration.class);

    @Autowired
    MarketParamRepository marketParamRepository;

    @Autowired
    StockHistoricalDataRepository stockHistoricalDataRepository;

    public void syncHistoricalByProduct(List<Stock> stockList) {
        try {
            MarketParam urlParam = marketParamRepository.findByCode(CodeParamEnum.URL_HISTORICAL_DATA_STOCK);
            MarketParam token = marketParamRepository.findByCode(CodeParamEnum.TOKEN_HISTORICAL_DATA_STOCK);
            RestTemplate restTemplate = RestTemplateUtil.getRestTemplateTrustStrategy();
            ObjectMapper objectMapper = new ObjectMapper();

            List<StockHistoricalData> historicalDataList = new ArrayList<>();

            logger.info("## INICIO CONSULTA DE HISTORICO");
            for (Stock stock : stockList) {
                logger.info("########## START STOCK : " + stock.getCode() + " ##############");
                String url = getFormatURL(stock, urlParam, token);
                String json = restTemplate.getForObject(url, String.class);
                MarketStackDTO marketStackDTO = objectMapper.readValue(json.toString(), MarketStackDTO.class);
                if (marketStackDTO != null && marketStackDTO.getData() != null && !marketStackDTO.getData().isEmpty()) {
                    logger.info("########## STOCK : " + stock.getCode() + " ENCONTROU: " + marketStackDTO.getData().size() + " Registros ##############");
                    for (MarketStackData data : marketStackDTO.getData()) {
                        StockHistoricalData historicalData = new StockHistoricalData();
                        historicalData.setClose(data.getClose());
                        String symbol = data.getSymbol().replace("." + stock.getExchange(), "");
                        historicalData.setCodeStock(symbol);
                        Calendar dateQuote = Calendar.getInstance();
                        dateQuote.setTime(data.getDate());
                        Calendar dateQuoteBr = Calendar.getInstance(TimeZone.getTimeZone("America/Sao_Paulo"));
                        dateQuoteBr.set(dateQuote.get(Calendar.YEAR),dateQuote.get(Calendar.MONTH),dateQuote.get(Calendar.DAY_OF_MONTH));
                        historicalData.setDateQuote(dateQuoteBr.getTime());
                        historicalData.setExchange(stock.getExchange());
                        historicalData.setHigh(data.getHigh());
                        historicalData.setLow(data.getLow());
                        historicalData.setOpen(data.getOpen());
                        historicalData.setVolume(data.getVolume());
                        historicalDataList.add(historicalData);
                    }
                } else {
                    logger.info("########## STOCK : " + stock.getCode() + " NÃO ENCONTROU  Registros ##############");
                }
            }
            logger.info("## FIM CONSULTA DE HISTORICO");
            logger.info("## SALVAR OS HISTÍCOS, QUANTIDADE: " + historicalDataList.size());
            stockHistoricalDataRepository.saveAll(historicalDataList);
        } catch (Exception e) {
            throw new BusinessException(MessageException.MSG_GENERAL_ERROR.getMessage(), MessageException.MSG_GENERAL_SYNC_ERROR.getMessage());
        }
    }

    private String getFormatURL(Stock stock, MarketParam urlParam, MarketParam token) {
        Date maxData = getLastDayByHistoricalStock(stock);
        String url = urlParam.getValue().replace("{0}", token.getValue());
        url = url.replace("{1}", stock.getCode() + "." + stock.getExchange());
        url = url.replace("{2}", DateUtil.format(maxData, "yyyy-MM-dd"));
        url = url.replace("{3}", DateUtil.format(new Date(), "yyyy-MM-dd"));
        return url;
    }

    private Date getLastDayByHistoricalStock(Stock stock) {
        Date maxData = stockHistoricalDataRepository.getMaxDateQuoteByCodeStock("VALE3");
        if (maxData == null) {
            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate now = LocalDate.now();
            now = now.minusYears(10);
            maxData = Date.from(now.atStartOfDay(defaultZoneId).toInstant());
        }
        return maxData;
    }
}
