package com.myservice.mymarket.model.dto.cedro;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketCedroData {
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal lastTrade;
    private BigDecimal bid;
    private BigDecimal ask;
    private BigDecimal change;
    private BigDecimal volumeFinancier;
    private BigDecimal volumeAmount;
    private BigDecimal quantityTrades;
    private String symbol;
    private String dateTrade;
    private String messageError;
}
