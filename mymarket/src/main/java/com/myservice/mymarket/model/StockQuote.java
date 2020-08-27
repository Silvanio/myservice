package com.myservice.mymarket.model;

import com.myservice.common.domain.MyEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "tb_stock_quote", schema = "market")
public class StockQuote extends MyEntity {

    @Column(name = "dt_quote")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateQuote;

    @Column(name = "code_stock")
    private String codeStock;

    private String exchange;
    private BigDecimal open;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal volume;
    private BigDecimal price;

    @Column(name = "price_variation_day")
    private BigDecimal priceVariationDay;

}
