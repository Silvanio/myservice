package com.myservice.mymarket.model.dto;

import com.myservice.common.dto.common.MyDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class StockQuoteDTO extends MyDTO {
    private Date dateQuote;
    private String codeStock;
    private String exchange;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal volume;
    private BigDecimal price;
    private BigDecimal priceVariationDay;
    private StockDTO stock;
    private List<StockHistoricalDataDTO> stockHistoricalDataList;
    private Long idProduct;
}
