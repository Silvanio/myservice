package com.myservice.mymarket.model.dto.marktstack;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketStackData {
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private String symbol;
    private String exchange;
    private Date date;
}
