package com.myservice.mymarket.model.dto.mercadobtc;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketMercadoBTC {
    private TickerCryptoDTO ticker;
}
