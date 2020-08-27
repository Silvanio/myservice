package com.myservice.mymarket.model.dto.mercadobtc;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerCryptoDTO {
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal last;
    private BigDecimal vol;
    private Long date;
}
